import Button from "@/components/common/button"
import Input from "@/components/common/input"

function Login() {
    return (
        <>
            <main className="min-h-screen flex items-center justify-center bg-blue-50 p-4">
                <div className="w-full max-w-md bg-white shadow-lg rounded-2xl p-6">
                    <h1 className="w-full text-center text-3xl font-bold text-blue-500 mb-16">
                        로그인
                    </h1>
                    <div className="flex flex-col gap-4">
                        <Input label="아이디" />
                        <Input label="비밀번호" type="password" />
                        <Button>로그인</Button>
                    </div>
                </div>
            </main>
        </>
    )
}

export default Login
