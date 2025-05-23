"use client"

import Button from "@/components/common/button"
import Input from "@/components/common/input"
import { useRouter } from "next/navigation"

function Signup() {
    const router = useRouter()

    return (
        <>
            <main className="min-h-screen flex items-center justify-center bg-blue-50 p-4">
                <div className="w-full max-w-md bg-white shadow-lg rounded-2xl p-6">
                    <h1 className="w-full text-center text-3xl font-bold text-blue-500 mb-16">
                        회원가입
                    </h1>
                    <div className="flex flex-col gap-4">
                        <Input label="이름" />
                        <Input label="아이디" />
                        <Input label="비밀번호" type="password" />
                        <div className="w-full">
                            <Button>회원가입</Button>
                            <p className="text-center">
                                이미 회원이신가요 ?{" "}
                                <span
                                    className="font-semibold text-blue-500 cursor-pointer"
                                    onClick={() => router.push("/login")}
                                >
                                    로그인
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </main>
        </>
    )
}

export default Signup
