import {createContext, useState} from "react";
import './App.css';
import Card from "./components/card/Card";

import Header from "./components/header/Header";
import LoadingSpinner from "./components/loadingSpinner/LoadingSpinner";
import SearchBar from "./components/searchBar/SearchBar";
import IProfileResponse from "./interfaces/IProfileResponse";

interface ILoadingContext {
    isLoading: boolean,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
}

interface IProfileContext {
    profiles: IProfileResponse[],
    setProfiles: React.Dispatch<React.SetStateAction<IProfileResponse[]>>
}

export const LoadingContext = createContext({} as ILoadingContext);

export const ProfileContext = createContext({} as IProfileContext)


function App() {
    const [isLoading, setLoading] = useState<boolean>(false);
    const [profiles, setProfiles] = useState<IProfileResponse[]>([]);


    return <>
        <LoadingContext.Provider value={{isLoading, setLoading}}>
            <ProfileContext.Provider value={{profiles, setProfiles}}>
                <LoadingSpinner/>
                <main className='container'>
                    <Header/>
                    <SearchBar/>
                    <div className='board'>
                        {profiles && profiles.map((profile) => (
                            <Card key={profile.id} idProfileResponse={profile.id}/>
                        ))}
                    </div>
                </main>
            </ProfileContext.Provider>
        </LoadingContext.Provider>
    </>;
}

export default App
