"use client"

import { useState } from "react"
import { useRouter, useParams } from "next/navigation"
import { dummy } from "@/dummy/data"
import Input from "@/components/common/input"

export default function BoardEditPage() {
    const { boardId } = useParams()
    const router = useRouter()
    const board = dummy.find((b) => b.id === Number(boardId))
    const [title, setTitle] = useState(board?.title || "")
    const [content, setContent] = useState(board?.content || "")

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        router.push(`/boards/${boardId}`)
    }

    return (
        <div className="w-full h-screen flex justify-center items-center">
            <div className="w-1/2">
                <h1 className="text-2xl font-bold text-blue-600 mb-4">
                    게시글 수정
                </h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <Input
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <Input
                        input={false}
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                    />
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-4 py-2 rounded"
                    >
                        수정
                    </button>
                </form>
            </div>
        </div>
    )
}
