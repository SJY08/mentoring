"use client"

import { dummy } from "@/dummy/data"
import { useRouter } from "next/navigation"
import { use } from "react"

function BoardDetail({ params }: { params: Promise<{ id: number }> }) {
    const { id } = use(params)
    const board = dummy.find((b) => b.id === Number(id))
    if (!board) return <div>게시글을 찾을 수 없습니다.</div>

    const router = useRouter()

    return (
        <div className="w-full min-h-screen flex justify-center items-start">
            <div className="mt-10 w-1/2">
                <h1 className="text-3xl font-bold text-blue-600 mb-4">
                    {board.title}
                </h1>

                <div className="flex justify-center items-center">
                    <p className="text-sm text-gray-500">
                        작성자: {board.username} / 작성일:{" "}
                        {new Date(board.date).toLocaleString()}
                    </p>
                    <div className="ml-auto flex gap-1">
                        <button
                            onClick={() =>
                                router.push(`/boards/${board.id}/edit`)
                            }
                            className="text-md text-white pr-4 pl-4 pb-0.5 pt-0.5 bg-blue-500 hover:bg-blue-600 rounded-sm duration-200 outline-none"
                        >
                            수정하기
                        </button>
                        <button className="text-md text-white pr-4 pl-4 pb-0.5 pt-0.5 bg-red-500 hover:bg-red-600 rounded-sm duration-200 outline-none">
                            삭제하기
                        </button>
                    </div>
                </div>
                <hr className="mt-4 mb-12 text-gray-400" />

                <p className="text-gray-700 mb-2">{board.content}</p>
            </div>
        </div>
    )
}

export default BoardDetail
