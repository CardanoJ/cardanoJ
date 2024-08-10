import './App.css'
import image from "./assets/cardanoj.png"


function App() {
    return (
        <section className="section custom-gradient">
            <div className='bg-attached' >

            </div>
            <div className="inner-div">
                {/* logo image */}
                <img src={image} alt="Logo" className="logo" />
                <h1 className="text-center">Welcome to CardanoJ</h1>
                <p className="learn-more">
                    <a href="https://github.com/CardanoJ/cardanoJ" target='_blank' className="learn-more-link">
                        Learn more
                    </a>

                </p>
            </div>
        </section>
    )
}

export default App