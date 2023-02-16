import './SearchBar.css'

import axios from 'axios';
import {useContext, useState} from "react";
import {LoadingContext, ProfileContext} from "../../App";
import IProfileResponse from '../../interfaces/IProfileResponse'

const SearchBar = () => {
    const [login, setLogin] = useState('');

    const {profiles, setProfiles} = useContext(ProfileContext)
    const {setLoading} = useContext(LoadingContext)

    const onSubmit = async (e:React.FormEvent) => {
        e.preventDefault();
        try {
            setLoading(true)
            const promisse = await axios.get<IProfileResponse>(`http://localhost:8080/profile-info/${login}`);
            const profileResponse = promisse.data;

            if(!profiles.find(profile => profile.id === profileResponse.id)) {
                setProfiles([...profiles, profileResponse]);
                setLogin('');
            }
        }catch(e){
            console.log("Erro", e);
        } finally {
            setLoading(false);
        }
    }

    return <div className='container__search-bar'>
        <form className='form__search-bar' onSubmit={e => onSubmit(e)}>
            <label htmlFor='loginUser' className='label__search-bar'>Digite um perfil de usuário:</label>
            <input id='loginUser'
                   placeholder='Ex: joaofanchini'
                   className='input__search-bar'
                   type='text'
                   value={login}
                   onChange={e => setLogin(e.target.value)}
            />
            <button className='button__search-bar' type='submit'>Buscar</button>
        </form>
    </div>
}

export default SearchBar;