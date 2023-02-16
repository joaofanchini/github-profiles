import {useContext} from "react";
import {ProfileContext} from "../../App";
import './Card.css'

interface CardProperties {
    idProfileResponse: number
}

const Card = ({idProfileResponse}: CardProperties) => {
    const {profiles} = useContext(ProfileContext);

    const profile = profiles.find(profile => profile.id === idProfileResponse);

    return <div className='card'>
        <h1 className='card__title'>Login: {profile && profile.login}</h1>
        <img className='card__image' src={profile && profile.avatarUrl}
             alt='Profile Picture'/>
        <h3 className='card__complement'>Id: {profile &&  profile.id}</h3>
        <div className='card__aditional-info'>
            <a className='card__linker' href='#'>Seguidores:<span className='card__counter'>{profile && profile.followers.length}</span></a>
            <a className='card__linker' href='#'>Repositórios:<span className='card__counter'>{profile && profile.repositories.length}</span></a>
        </div>
    </div>
}

export default Card;