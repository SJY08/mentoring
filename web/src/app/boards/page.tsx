"use client"

import { useRouter } from "next/navigation"
import { dummy } from "@/dummy/data"

function Boards() {
    const router = useRouter()

    return (
        <div className="w-full min-h-screen flex justify-center items-start bg-white">
            <div className="mt-10 w-1/2 ">
                <div className="flex justify-center items-center">
                    <h1 className="flex justify-center items-center text-2xl font-bold text-blue-500 mb-4">
                        게시판 목록
                    </h1>

                    <button className="ml-auto text-md text-white pr-4 pl-4 pb-0.5 pt-0.5 bg-blue-500 hover:bg-blue-600 rounded-sm duration-200 outline-none">
                        작성하기
                    </button>
                </div>
                <hr className="mb-12 text-gray-400" />
                <ul className="space-y-2">
                    {dummy.map((board) => (
                        <li
                            key={board.id}
                            className="p-4 border rounded hover:bg-blue-50"
                        >
                            <p
                                onClick={() =>
                                    router.push(`/boards/${board.id}`)
                                }
                                className="text-lg font-semibold text-blue-700"
                            >
                                {board.title}
                            </p>
                            <p className="text-sm text-gray-500">
                                작성자: {board.username} / 작성일:{" "}
                                {new Date(board.date).toLocaleString()}
                            </p>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default Boards
