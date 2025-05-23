import React from "react"

interface props {
    children?: React.ReactNode
}

function Button({ children }: props) {
    return (
        <div className="mt-2 flex justify-center items-center bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-xl transition-colors duration-300">
            {children}
        </div>
    )
}

export default Button
