import './LoadingSpinner.css';
import {useContext} from "react";
import {LoadingContext} from "../../App";

const LoadingSpinner = () => {
    const {isLoading} = useContext(LoadingContext);
    console.log('isLoading',isLoading)
    return <div style={{
    visibility: !!isLoading && isLoading ? 'visible': 'hidden'}
    } className="loader-container">
        <div className="spinner"></div>
    </div>
}

export default LoadingSpinner;