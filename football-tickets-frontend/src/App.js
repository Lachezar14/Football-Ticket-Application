
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import SignInPage from "./components/pages/SignInPage";
import HomeImage from "./components/HomeImage";
import RegisterPage from "./components/pages/RegisterPage";
import MatchesPage from "./components/pages/MatchCardPage";
import TicketsSalePage from "./components/pages/TicketsSalePage";
import ProfilePage from "./components/pages/ProfilePage";
import AdminPage from "./components/pages/AdminPage"; 
import AdminMatchPage from "./components/pages/AdminMatchPage";
import AdminTeamPage from "./components/pages/AdminTeamPage";
import StatisticsPage from "./components/pages/StatisticsPage";
import GroupChatPage from "./components/groupChat/GroupChatPage";

function App() {
    //TODO uncomment when final version is ready
    //const user = Api.getCurrentUser();
    
  return (
    <div>
        
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />} >
                    <Route index element={<HomeImage />} />
                    <Route path="/matches" element={<MatchesPage />} />
                    <Route path="/matches/ticketSale" element={<TicketsSalePage />} />
                    <Route path="/login" element={<SignInPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path='/profile' element={<ProfilePage/>} />
                    <Route path='/chat' element={<GroupChatPage />} />
                    <Route path='/admin' element={<AdminPage/>} />
                    <Route path='/admin/matches' element={<AdminMatchPage/>} />
                    <Route path='/admin/teams' element={<AdminTeamPage/>} />
                    <Route path='/admin/statistics' element={<StatisticsPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>

        {/*
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<HomePageLayout />} />
                <Route path='/matches' element={<MatchesListLayout />} />
                <Route path='/matches/ticketSale' element={<TicketsSaleLayout />} />
                <Route path='/login' element={<SignInLayout/>} />
                <Route path='/register' element={<RegisterLayout/>} />
                <Route path='/chat' element={<GroupChatLayout/>} />

                TODO used for development. Remove when final version is ready
                <Route path='/profile' element={<ProfileLayout/>} />
                <Route path='/admin' element={<AdminLayout/>} />
                <Route path='/admin/matches' element={<AdminMatchLayout/>} />
                <Route path='/admin/teams' element={<AdminTeamLayout/>} />
                <Route path='/admin/statistics' element={<StatisticsLayout/>}/>

                TODO uncomment protected routes when final version is ready
                <Route path='/profile' element={ user && user.role === "USER" ? <ProfileLayout/> : <HomePageLayout/>}/>
                <Route path='/admin' element={ user && user.role === "ADMIN" ? <AdminLayout/> : <HomePageLayout/>}/>
                <Route path='/admin/matches' element={ user && user.role === "ADMIN" ? <AdminMatchLayout/> : <HomePageLayout/>}/>
                <Route path='/admin/teams' element={ user && user.role === "ADMIN" ? <AdminTeamLayout/> : <HomePageLayout/>}/>
              
            </Routes>
        </BrowserRouter>
        */}
    </div>
  );
}

export default App;
