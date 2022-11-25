import ProfilePage from '../components/pages/ProfilePage';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

export default function ProfileLayout() {
    return (
        <div>
            <Navbar />
            <ProfilePage />
            <Footer />
        </div>
    );
}