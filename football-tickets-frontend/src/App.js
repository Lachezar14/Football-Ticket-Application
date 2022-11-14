
import HomePageLayout from "./layouts/HomePageLayout";
import SignInLayout from "./layouts/SignInLayout";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import RegisterLayout from "./layouts/RegisterLayout";
import MatchesListLayout from "./layouts/MatchesListLayout";
import ProfileLayout from "./layouts/ProfileLayout";
import AdminLayout from "./layouts/AdminLayout";
import AdminMatchLayout from "./layouts/AdminMatchLayout";
import AdminTeamLayout from "./layouts/AdminTeamLayout";
import TicketsSaleLayout from "./layouts/TicketsSaleLayout";
import GroupChatLayout from "./components/groupChat/GroupChatLayout";

function App() {
    //TODO uncomment when final version is ready
    //const user = Api.getCurrentUser();
    
  return (
    <div>
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<HomePageLayout />} />
                <Route path='/matches' element={<MatchesListLayout />} />
                <Route path='/matches/ticketSale' element={<TicketsSaleLayout />} />
                <Route path='/login' element={<SignInLayout/>} />
                <Route path='/register' element={<RegisterLayout/>} />
                <Route path='/chat' element={<GroupChatLayout/>} />

                {/*TODO used for development. Remove when final version is ready*/}
                <Route path='/profile' element={<ProfileLayout/>} />
                <Route path='/admin' element={<AdminLayout/>} />
                <Route path='/admin/matches' element={<AdminMatchLayout/>} />
                <Route path='/admin/teams' element={<AdminTeamLayout/>} />

                {/* TODO uncomment protected routes when final version is ready
                <Route path='/profile' element={ user && user.role === "USER" ? <ProfileLayout/> : <HomePageLayout/>}/>
                <Route path='/admin' element={ user && user.role === "ADMIN" ? <AdminLayout/> : <HomePageLayout/>}/>
                <Route path='/admin/matches' element={ user && user.role === "ADMIN" ? <AdminMatchLayout/> : <HomePageLayout/>}/>
                <Route path='/admin/teams' element={ user && user.role === "ADMIN" ? <AdminTeamLayout/> : <HomePageLayout/>}/>
                */}
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
