import Navbar from "../components/Navbar";
import MatchCardPage from "../components/pages/MatchCardPage";
import Footer from "../components/Footer";


export default function MatchesListLayout() {
    return (
        <div>
            <Navbar />
            <MatchCardPage />
            <Footer />
        </div>
    );
}