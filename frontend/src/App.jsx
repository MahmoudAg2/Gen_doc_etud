import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import AdminDashboard from "./pages/AdminDashboard";
import ProfesseurDashboard from "./pages/ProfesseurDashboard";
import EtudiantDashboard from "./pages/EtudiantDashboard";

function PrivateRoute({ children, roleRequired }) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (!token) return <Navigate to="/" />;
  if (roleRequired && role !== roleRequired) return <Navigate to="/" />;

  return children;
}

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50 flex flex-col font-sans">
        <Routes>
          <Route path="/" element={<Login />} />
          
          <Route path="/admin" element={
            <PrivateRoute roleRequired="ROLE_ADMIN">
              <AdminDashboard />
            </PrivateRoute>
          } />
          
          <Route path="/professeur" element={
            <PrivateRoute roleRequired="ROLE_PROF">
              <ProfesseurDashboard />
            </PrivateRoute>
          } />
          
          <Route path="/etudiant" element={
            <PrivateRoute roleRequired="ROLE_ETUDIANT">
              <EtudiantDashboard />
            </PrivateRoute>
          } />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
