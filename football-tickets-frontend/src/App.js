
import HomePage from "./pages/HomePage";
import SignInPage from "./pages/SignInPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import RegisterPage from "./pages/RegisterPage";
import MatchesListPage from "./pages/MatchesListPage";
import ProfilePage from "./pages/ProfilePage";
import AdminPage from "./pages/AdminPage";
import AdminMatchPage from "./pages/AdminMatchPage";
import AdminTeamPage from "./pages/AdminTeamPage";
import TicketsSalePage from "./pages/TicketsSalePage";

function App() {
  return (
    <div>
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<HomePage />} />
                <Route path='/matches' element={<MatchesListPage />} />
                <Route path='/ticketSale' element={<TicketsSalePage />} />
                <Route path='/login' element={<SignInPage/>} />
                <Route path='/register' element={<RegisterPage/>} />
                <Route path='/profile' element={<ProfilePage/>} />
                <Route path='/admin' element={<AdminPage/>} />
                <Route path='/admin/matches' element={<AdminMatchPage/>} />
                <Route path='/admin/teams' element={<AdminTeamPage/>} />
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
