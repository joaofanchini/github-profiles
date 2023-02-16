import './SearchBar.css'

const SearchBar = () => {
    return <div className='container__search-bar'>
        <form className='form__search-bar' onSubmit={() => console.log('submit')}>
            <label htmlFor='loginUser' className='label__search-bar'>Digite um perfil de usuário:</label>
            <input id='loginUser'
                   placeholder='Ex: joaofanchini'
                   className='input__search-bar'
                   type='text'
            />
            <button className='button__search-bar' type='submit'>Buscar</button>
        </form>
    </div>
}

export default SearchBar;