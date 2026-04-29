import { Edit, Mail, LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";

export default function ProfesseurDashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <header className="flex justify-between items-center bg-white p-4 shadow-sm rounded-xl mb-8">
        <h1 className="text-2xl font-bold text-gray-800">Espace Professeur</h1>
        <button onClick={handleLogout} className="text-red-500 flex items-center space-x-1 hover:text-red-700">
          <LogOut size={18} /> <span>Déconnexion</span>
        </button>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition">
          <Edit size={40} className="text-blue-500 mb-4" />
          <h2 className="text-xl font-bold mb-2">Saisie des notes</h2>
          <p className="text-gray-500">Ajoutez ou modifiez les notes de vos étudiants pour vos matières.</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition">
          <Mail size={40} className="text-purple-500 mb-4" />
          <h2 className="text-xl font-bold mb-2">Lettres de Recommandation</h2>
          <p className="text-gray-500">Générez une lettre de recommandation PDF pré-remplie pour un étudiant.</p>
        </div>
      </div>
    </div>
  );
}
