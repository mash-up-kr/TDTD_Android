package com.tdtd.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.usecase.*
import com.tdtd.domain.util.State
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.util.MainCoroutineRule
import com.tdtd.presentation.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val roomRepository: RoomRepository = mockk()
    private val bookRepository: BookmarkRepository = mockk()

    private lateinit var getAllRoomsUseCase: GetAllRoomsUseCase
    private lateinit var getAllBookmarksUseCase: GetAllBookmarksUseCase
    private lateinit var addBookMarkUseCase: AddBookMarkUseCase
    private lateinit var deleteBookMarkUseCase: DeleteBookMarkUseCase
    private lateinit var createUserRoomUseCase: CreateUserRoomUseCase
    private lateinit var enterByRoomCodeUseCase: EnterByRoomCodeUseCase

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        getAllRoomsUseCase = GetAllRoomsUseCase(roomRepository)
        getAllBookmarksUseCase = GetAllBookmarksUseCase(bookRepository)
        addBookMarkUseCase = AddBookMarkUseCase(bookRepository)
        deleteBookMarkUseCase = DeleteBookMarkUseCase(bookRepository)
        createUserRoomUseCase = CreateUserRoomUseCase(roomRepository)
        enterByRoomCodeUseCase = EnterByRoomCodeUseCase(roomRepository)

        mainViewModel = MainViewModel(
            getAllRoomsUseCase,
            getAllBookmarksUseCase,
            addBookMarkUseCase,
            deleteBookMarkUseCase,
            createUserRoomUseCase,
            enterByRoomCodeUseCase
        )
    }

    @Test
    fun `get user room list load success`() = mainCoroutineRule.runBlockingTest {
        val roomList = listOf(
            RoomEntity(
                "f",
                "z5ivVroAQMiCgWJ0SLFgSg",
                "https://sokdak.page.link/H4zsTy2ogXAZKKd18",
                "VOICE",
                "2021-05-11T23:09:59",
                isBookmark = false,
                isHost = false
            ),
            RoomEntity(
                "ㅅ",
                "uiWc0K_yQ4axJt5XoR4AgQ",
                "https://sokdak.page.link/DspHBB6j7SvPhTZ56",
                "VOICE",
                "2021-07-12T22:31:14",
                isBookmark = false,
                isHost = false
            )
        )

        coEvery { getAllRoomsUseCase.invoke() } returns State.Success(roomList)
        mainViewModel.getUserRoomList()

        val result = mainViewModel.roomList.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(mainViewModel.roomList.value, result)

        val emptyRoom = mainViewModel.emptyRoom.getOrAwaitValue()
        Assert.assertEquals(emptyRoom, false)

        coVerify { getAllRoomsUseCase.invoke() }
    }

    @Test
    fun `get user book mark list load success`() = mainCoroutineRule.runBlockingTest {
        val bookMarkList = listOf(
            RoomEntity(
                "f",
                "z5ivVroAQMiCgWJ0SLFgSg",
                "https://sokdak.page.link/H4zsTy2ogXAZKKd18",
                "VOICE",
                "2021-05-11T23:09:59",
                isBookmark = false,
                isHost = false
            ),
            RoomEntity(
                "ㅅ",
                "uiWc0K_yQ4axJt5XoR4AgQ",
                "https://sokdak.page.link/DspHBB6j7SvPhTZ56",
                "VOICE",
                "2021-07-12T22:31:14",
                isBookmark = false,
                isHost = false
            )
        )

        coEvery { getAllBookmarksUseCase.invoke() } returns State.Success(bookMarkList)
        mainViewModel.getUserBookmarkList()

        val result = mainViewModel.roomList.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(mainViewModel.roomList.value, result)

        coVerify { getAllBookmarksUseCase.invoke() }
    }

    @Test
    fun `post book mark by room code`() = mainCoroutineRule.runBlockingTest {
        coEvery { addBookMarkUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns mockk()
        mainViewModel.postBookmarkByRoomCode(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")
        coVerify { addBookMarkUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }

    @Test
    fun `delete book mark by room code`() = mainCoroutineRule.runBlockingTest {
        coEvery { deleteBookMarkUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns mockk()
        mainViewModel.deleteBookmarkByRoomCode(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")
        coVerify { deleteBookMarkUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }

    @Test
    fun `post create user room load success`() = mainCoroutineRule.runBlockingTest {
        val mockRoomEntity = MakeRoomEntity(
            "test",
            RoomTypeEntity.TEXT
        )

        val mockCreatedRoomEntity = CreatedRoomCodeEntity(
            2000,
            "success",
            RoomCodeEntity("3ivfXQCzSia-ZpHSvqOWKg")
        )

        coEvery { createUserRoomUseCase.invoke(param = mockRoomEntity) } returns State.Success(
            mockCreatedRoomEntity
        )
        mainViewModel.postCreateUserRoom(makeRoomEntity = mockRoomEntity)

        val result = mainViewModel.makeRoom.getOrAwaitValue()

        Assert.assertNotNull(result)
        Assert.assertEquals(mainViewModel.makeRoom.value, result)

        coVerify { createUserRoomUseCase.invoke(param = mockRoomEntity) }
    }

    @Test
    fun `post participate by room code`() = mainCoroutineRule.runBlockingTest {
        coEvery { enterByRoomCodeUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") } returns mockk()
        mainViewModel.postParticipateByRoomCode(roomCode = "z5ivVroAQMiCgWJ0SLFgSg")
        coVerify { enterByRoomCodeUseCase.invoke(param = "z5ivVroAQMiCgWJ0SLFgSg") }
    }
}