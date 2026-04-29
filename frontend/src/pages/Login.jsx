import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Lock, User } from "lucide-react";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email: email,
        motDePasse: password
      });
      
      const { token, role } = response.data;
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      if (role === "ROLE_ADMIN") navigate("/admin");
      else if (role === "ROLE_PROF") navigate("/professeur");
      else navigate("/etudiant");
      
    } catch (err) {
      setError("Identifiants incorrects ou erreur serveur");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500">
      <div className="glass p-10 w-full max-w-md">
        <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">GenDoc Étudiant</h1>
        <form onSubmit={handleLogin} className="space-y-6">
          {error && <p className="text-red-500 text-sm text-center">{error}</p>}
          <div className="relative">
            <User className="absolute left-3 top-3 text-gray-400" size={20} />
            <input 
              type="text" 
              placeholder="Email" 
              className="w-full pl-10 pr-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500 outline-none"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required 
            />
          </div>
          <div className="relative">
            <Lock className="absolute left-3 top-3 text-gray-400" size={20} />
            <input 
              type="password" 
              placeholder="Mot de passe" 
              className="w-full pl-10 pr-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500 outline-none"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required 
            />
          </div>
          <button type="submit" className="w-full bg-purple-600 hover:bg-purple-700 text-white font-semibold py-3 rounded-lg transition duration-200">
            Se connecter
          </button>
        </form>
      </div>
    </div>
  );
}
