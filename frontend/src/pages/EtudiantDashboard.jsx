import { Download, CalendarDays, IdCard, LogOut } from "lucide-react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function EtudiantDashboard() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  const handleDownloadCarte = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/documents/carte/1", {
        responseType: 'blob',
        headers: { Authorization: `Bearer ${token}` }
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `carte_etudiant.pdf`);
      document.body.appendChild(link);
      link.click();
    } catch (e) {
      alert("Erreur lors du téléchargement");
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <header className="flex justify-between items-center bg-white p-4 shadow-sm rounded-xl mb-8">
        <h1 className="text-2xl font-bold text-gray-800">Espace Étudiant</h1>
        <button onClick={handleLogout} className="text-red-500 flex items-center space-x-1 hover:text-red-700">
          <LogOut size={18} /> <span>Déconnexion</span>
        </button>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex flex-col items-center justify-center text-center">
          <IdCard size={48} className="text-indigo-500 mb-4" />
          <h2 className="text-xl font-bold mb-2">Carte d'Étudiant</h2>
          <p className="text-gray-500 mb-6">Téléchargez votre carte étudiante officielle au format PDF.</p>
          <button onClick={handleDownloadCarte} className="flex items-center space-x-2 bg-indigo-600 text-white px-6 py-2 rounded-full hover:bg-indigo-700 transition">
            <Download size={18} /> <span>Télécharger PDF</span>
          </button>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex flex-col items-center justify-center text-center">
          <CalendarDays size={48} className="text-pink-500 mb-4" />
          <h2 className="text-xl font-bold mb-2">Emploi du temps</h2>
          <p className="text-gray-500 mb-6">Consultez votre emploi du temps hebdomadaire.</p>
          <button className="flex items-center space-x-2 bg-pink-600 text-white px-6 py-2 rounded-full hover:bg-pink-700 transition">
            <Download size={18} /> <span>Télécharger PDF</span>
          </button>
        </div>
      </div>
    </div>
  );
}
