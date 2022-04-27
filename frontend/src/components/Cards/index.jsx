//import imageSrc from '@assets/img/1/1.jpg';
import './styles.css';

function Cards(props) {

    const veiculo = props.carro;
    const img = '/img/' + veiculo.id + '/1.jpg';
    //console.log(imageSrc)

    return (
        <section className="box">
            <div className="card">
                <div className="container">
                    <img src={img} alt="Carro" />
                </div>
                <ul className="dadosCarro">
                    <li className="modelo">{veiculo.modelo}</li>
                    <li>{veiculo.loja.nome}</li>
                    <li>{veiculo.quilometragem + 'km'}</li>
                    <li className="oferta">{'R$' + veiculo.preco}</li>
                </ul>
            </div>
        </section>
    )
}

export default Cards;