const nextConfig = {
    reactStrictMode: false,
    images: {},
    rewrites: async () => [
        {
            source: "/api/:path*",
            destination: process.env.BASE_URL + "/api/:path*",
        },
    ],
}
