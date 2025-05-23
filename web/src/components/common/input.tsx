"use client"

import { useState } from "react"
import { HiEye, HiEyeOff } from "react-icons/hi"

interface props {
    label?: string
    value?: string
    placeholder?: string
    type?: string
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
}

function Input({ label, value, placeholder, type, onChange }: props) {
    const [show, setShow] = useState<boolean>(false)

    return (
        <>
            <div className="w-full flex justify-center items-start flex-col gap-0.5">
                <label className="font-semibold text-lg">{label}</label>

                <div className="w-full h-8 flex justify-center items-center border-gray-500 border rounded-sm  box-border p-1">
                    <input
                        type={
                            type == "password"
                                ? show
                                    ? "text"
                                    : "password"
                                : type
                        }
                        value={value}
                        placeholder={placeholder}
                        onChange={onChange}
                        className="w-full h-full border-none outline-none"
                    />

                    {type == "password" && (
                        <div
                            onClick={() => setShow(!show)}
                            className="cursor-pointer"
                        >
                            {show ? <HiEye /> : <HiEyeOff />}
                        </div>
                    )}
                </div>
            </div>
        </>
    )
}

export default Input
