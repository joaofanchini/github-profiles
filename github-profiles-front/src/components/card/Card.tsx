import './Card.css'

const Card = () => {
    return <div className='card'>
        <h1 className='card__title'>login name</h1>
        <img className='card__image' src={'https://avatars.githubusercontent.com/u/31604369?v=4'} alt='Profile Picture'/>
        <h3 className='card__complement'>id</h3>
        <div className='card__aditional-info'>
            <a className='card__linker' href='#'>Seguidores:<span className='card__counter'>3</span></a>
            <a className='card__linker' href='#'>Repositórios:<span className='card__counter'>3</span></a>
        </div>
    </div>
}

export default Card;