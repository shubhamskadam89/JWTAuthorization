import React, { useState } from "react";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [jwt, setJwt] = useState("");
    const [profile, setProfile] = useState(null);

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/signin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });
            if (response.ok) {
                const data = await response.json();
                setJwt(data.jwtToken);
                fetchUserProfile(data.jwtToken);
                setMessage("Login successful!");
            } else {
                setMessage("Login failed.");
            }
        } catch (error) {
            console.error("Error:", error);
            setMessage("Something went wrong.");
        }
    };

    const fetchUserProfile = async (token) => {
        try {
            const response = await fetch("http://localhost:8080/profile", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setProfile(data);
    
            } else {
                setMessage("Failed to fetch profile.");
            }
        } catch (error) {
            console.error("Error:", error);
            setMessage("Something went wrong.");
        }
        
    };

    const handleLodout = ()=>{
        console.log(username," logged out")
        setJwt("");
        setMessage("");
        setPassword("");
        setProfile(null);
        setUsername("");
        
        
    }

    return (
        <div className="flex items-center justify-center h-full bg-gray-100">
            <div className="w-full max-w-md bg-white p-8 rounded-xl shadow-md">
                {!profile ? (
                    <form onSubmit={handleLogin} className="space-y-6">
                        <h2 className="text-2xl font-semibold text-gray-700 text-center">Login</h2>
                        <div>
                            <label className="block text-sm font-medium text-gray-600">Username</label>
                            <input
                                type="text"
                                className="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                placeholder="Enter your username"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-600">Password</label>
                            <input
                                type="password"
                                className="mt-1 w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Enter your password"
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition duration-200"
                        >
                            Login
                        </button>
                        {message && (
                            <p className="text-center text-sm text-red-600">{message}</p>
                        )}
                    </form>
                ) : (
                    <div className="space-y-6">
                        <h2 className="text-2xl font-bold text-center text-gray-700">
                            Welcome, {profile.username} 👋
                        </h2>

                        <div className="bg-white rounded-lg shadow-md p-6">
                            <h3 className="text-lg font-semibold text-gray-600 mb-4 border-b pb-2">
                                Profile Details
                            </h3>
                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-gray-700">
                                <div>
                                    <p className="text-sm font-medium text-gray-500">Username</p>
                                    <p className="text-base">{profile.username}</p>
                                </div>
                                <div>
                                    <p className="text-sm font-medium text-gray-500">Roles</p>
                                    <p className="text-base">{profile.roles.join(", ")}</p>
                                </div>
                                <div className="sm:col-span-2">
                                    <p className="text-sm font-medium text-gray-500">Message</p>
                                    <p className="text-base">{profile.msg}</p>
                                </div>
                            </div>
                        </div>

                        <div className="bg-gray-50 rounded-lg border p-4">
                            <h4 className="text-sm font-semibold text-gray-600 mb-2">Your JWT Token</h4>
                            <div className="bg-white p-3 rounded-md text-xs text-gray-500 overflow-x-auto max-h-32 break-words border">
                                {jwt}
                            </div>
                        </div>

                        {message && (
                            <div className="text-center text-green-600 text-sm font-medium">
                                {message}
                            </div>
                        )}
                         <button
                            onClick={handleLodout}
                            className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg transition duration-200"
                        >Log Out</button>
                       
                    </div>
                )}
            </div>
        </div>
    );
};

export default Login;
