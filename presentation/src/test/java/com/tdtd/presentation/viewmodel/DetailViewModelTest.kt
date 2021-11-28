package com.tdtd.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.usecase.*
import com.tdtd.domain.util.State
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.util.MainCoroutineRule
import com.tdtd.presentation.util.getOrAwaitValue
import com.tdtd.presentation.utils.MultiPartForm
import com.tdtd.presentation.utils.randomAngle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MultipartBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val replyRepository: ReplyRepository = mockk()
    private val adminRepository: AdminRepository = mockk()
    private val roomRepository: RoomRepository = mockk()

    private lateinit var getAllReplyUseCase: GetAllReplyUseCase
    private lateinit var modifyRoomNameUseCase: ModifyRoomNameUseCase
    private lateinit var deleteRoomByAdminUseCase: DeleteRoomByAdminUseCase
    private lateinit var deleteCommentByAdminUseCase: DeleteCommentByAdminUseCase
    private lateinit var getShareRoomUseCase: GetShareRoomUseCase
    private lateinit var getRoomDetailUseCase: GetRoomDetailUseCase
    private lateinit var deleteRoomByUserUseCase: DeleteRoomByUserUseCase
    private lateinit var deleteCommentByUserUseCase: DeleteCommentByUserUseCase
    private lateinit var reportCommentUseCase: ReportCommentUseCase

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        getAllReplyUseCase = GetAllReplyUseCase(replyRepository)
        modifyRoomNameUseCase = ModifyRoomNameUseCase(adminRepository)
        deleteRoomByAdminUseCase = DeleteRoomByAdminUseCase(adminRepository)
        deleteCommentByAdminUseCase = DeleteCommentByAdminUseCase(adminRepository)
        getShareRoomUseCase = GetShareRoomUseCase(adminRepository)
        getRoomDetailUseCase = GetRoomDetailUseCase(roomRepository)
        deleteRoomByUserUseCase = DeleteRoomByUserUseCase(roomRepository)
        deleteCommentByUserUseCase = DeleteCommentByUserUseCase(replyRepository)
        reportCommentUseCase = ReportCommentUseCase(replyRepository)

        detailViewModel = DetailViewModel(
            getAllReplyUseCase,
            modifyRoomNameUseCase,
            deleteRoomByAdminUseCase,
            deleteCommentByAdminUseCase,
            getShareRoomUseCase,
            getRoomDetailUseCase,
            deleteRoomByUserUseCase,
            deleteCommentByUserUseCase,
            reportCommentUseCase
        )
    }

    @Test
    fun `get room detail by room code load success`() = mainCoroutineRule.runBlockingTest {
        val mockRoomDetailEntity = RoomDetailEntity(
            "2000",
            "success",
            ResultRoomInfoEntity(
                "f", RoomTypeEntity.VOICE, "https://sokdak.page.link/H4zsTy2ogXAZKKd186",
                listOf(
                    UserDetailCommentEntity(
                        1,
                        "f",
                        null,
                        "https://tdtd-test-bucket.s3.ap-northeast-2.amazonaws.com/prod/voice-files/2021-05-11%2023%3A16%3A05.069842-recording.3gp",
                        StickerColorType.LIGHT_GREEN,
                        -2,
                        "2021-05-11T23:16:05",
                        false
                    ),
                    UserDetailCommentEntity(
                        2,
                        "ff",
                        null,
                        "https://tdtd-test-bucket.s3.ap-northeast-2.amazonaws.com/prod/voice-files/2021-05-11%2023%3A16%3A17.584743-recording.3gp",
                        StickerColorType.LIGHT_GREEN,
                        -9,
                        "2021-05-11T23:16:17",
                        false
                    ),
                    UserDetailCommentEntity(
                        25,
                        "f",
                        null,
                        "https://tdtd-test-bucket.s3.ap-northeast-2.amazonaws.com/prod/voice-files/2021-06-07%2010%3A19%3A50.88185-recording.mp3",
                        StickerColorType.LAVENDER,
                        5,
                        "2021-06-07T10:19:50",
                        false
                    )
                )
            )
        )

        coEvery { getRoomDetailUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns State.Success(
            mockRoomDetailEntity
        )
        detailViewModel.getRoomDetailByRoomCode(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")

        val result = detailViewModel.detailRoom.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(detailViewModel.detailRoom.value, result)

        coVerify { getRoomDetailUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }

    @Test
    fun `delete user room by room code`() = mainCoroutineRule.runBlockingTest {
        coEvery { deleteRoomByUserUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns mockk()
        detailViewModel.deleteParticipatedUserRoom(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")
        coVerify { deleteRoomByUserUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }

    @Test
    fun `create comment by user`() = mainCoroutineRule.runBlockingTest {
        val multipartBody: ArrayList<MultipartBody.Part> = ArrayList()

        multipartBody.apply {
            add(MultiPartForm.getBody("nickname", "nickname"))
            add(MultiPartForm.getBody("message_type", "TEXT"))
            add(MultiPartForm.getBody("text_message", "content"))
            add(
                MultiPartForm.getBody(
                    "sticker_color",
                    StickerColorType.values().random().toString()
                )
            )
            add(MultiPartForm.getBody("sticker_angle", randomAngle()))
        }

        coEvery {
            getAllReplyUseCase.invoke(
                "z5ivVroAQMiCgWJ0SLFgSg",
                multipartBody
            )
        } returns mockk()

        detailViewModel.postReplyUserComment(
            roomCode = "z5ivVroAQMiCgWJ0SLFgSg",
            params = multipartBody
        )

        coVerify { getAllReplyUseCase.invoke("z5ivVroAQMiCgWJ0SLFgSg", multipartBody) }
    }

    @Test
    fun `delete reply user comment`() = mainCoroutineRule.runBlockingTest {
        coEvery { deleteCommentByUserUseCase.invoke(param = 1) } returns mockk()
        detailViewModel.deleteReplyUserComment(commentId = 1)
        coVerify { deleteCommentByUserUseCase.invoke(param = 1) }
    }

    @Test
    fun `report comment by user`() = mainCoroutineRule.runBlockingTest {
        coEvery { reportCommentUseCase.invoke(param = 1) } returns mockk()
        detailViewModel.postReportUserByCommentId(commentId = 1)
        coVerify { reportCommentUseCase.invoke(param = 1) }
    }

    @Test
    fun `delete room by admin`() = mainCoroutineRule.runBlockingTest {
        coEvery { deleteRoomByAdminUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns mockk()
        detailViewModel.deleteRoomByHost(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")
        coVerify { deleteRoomByAdminUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }

    @Test
    fun `delete other users comment by admin`() = mainCoroutineRule.runBlockingTest {
        coEvery { deleteCommentByAdminUseCase.invoke(param = 1) } returns mockk()
        detailViewModel.deleteOtherCommentByAdmin(commentId = 1)
        coVerify { deleteCommentByAdminUseCase.invoke(param = 1) }
    }

    @Test
    fun `sharing room url loaded success`() = mainCoroutineRule.runBlockingTest {
        val mockRoomUrlEntity = RoomUrlEntity(
            2000,
            "success",
            SharedUrlEntity("https://sokdak.page.link/WEzPpKnWtEiL7cqt6")
        )

        coEvery { getShareRoomUseCase.invoke(param = "3ivfXQCzSia-ZpHSvqOWKg") } returns State.Success(
            mockRoomUrlEntity
        )
        detailViewModel.getSharedRoomUrl(roomCode = "3ivfXQCzSia-ZpHSvqOWKg")

        val result = detailViewModel.sharedUrl.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(detailViewModel.sharedUrl.value, result)

        coVerify { getShareRoomUseCase.invoke(param = "3ivfXQCzSia-ZpHSvqOWKg") }
    }

    @Test
    fun `modify room name by host`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            modifyRoomNameUseCase.invoke(
                "3ivfXQCzSia-ZpHSvqOWKg",
                ModifyRoomNameEntity("test")
            )
        } returns mockk()

        detailViewModel.modifyRoomNameByHost(
            roomCode = "3ivfXQCzSia-ZpHSvqOWKg",
            ModifyRoomNameEntity("test")
        )

        coVerify {
            modifyRoomNameUseCase.invoke(
                "3ivfXQCzSia-ZpHSvqOWKg",
                ModifyRoomNameEntity("test")
            )
        }
    }
}