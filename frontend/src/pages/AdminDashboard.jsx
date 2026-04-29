import { BookOpen, FileText, UserSquare, LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";

export default function AdminDashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="min-h-screen bg-gray-100 flex">
      {/* Sidebar */}
      <aside className="w-64 bg-white shadow-xl flex flex-col">
        <div className="p-6 border-b">
          <h2 className="text-2xl font-bold text-indigo-600">Admin Panel</h2>
        </div>
        <nav className="flex-1 p-4 space-y-2">
          <button className="w-full flex items-center space-x-3 text-left p-3 hover:bg-indigo-50 text-gray-700 rounded-lg transition">
            <UserSquare size={20} /> <span>Gérer Utilisateurs</span>
          </button>
          <button className="w-full flex items-center space-x-3 text-left p-3 hover:bg-indigo-50 text-gray-700 rounded-lg transition">
            <BookOpen size={20} /> <span>Gérer Filières & Classes</span>
          </button>
          <button className="w-full flex items-center space-x-3 text-left p-3 hover:bg-indigo-50 text-gray-700 rounded-lg transition">
            <FileText size={20} /> <span>Tous les Documents</span>
          </button>
        </nav>
        <div className="p-4 border-t">
          <button onClick={handleLogout} className="w-full flex items-center justify-center space-x-2 bg-red-50 text-red-600 p-2 rounded-lg hover:bg-red-100 transition">
            <LogOut size={20} /> <span>Déconnexion</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 p-8">
        <h1 className="text-3xl font-bold text-gray-800 mb-6">Tableau de Bord - Administrateur</h1>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-indigo-500">
             <h3 className="text-gray-500 mb-1">Étudiants</h3>
             <p className="text-2xl font-bold">120</p>
          </div>
          <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-purple-500">
             <h3 className="text-gray-500 mb-1">Professeurs</h3>
             <p className="text-2xl font-bold">14</p>
          </div>
          <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-green-500">
             <h3 className="text-gray-500 mb-1">Documents Générés</h3>
             <p className="text-2xl font-bold">342</p>
          </div>
        </div>
      </main>
    </div>
  );
}
