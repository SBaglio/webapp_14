
document.addEventListener("DOMContentLoaded", function() {
    caricaRecensioni();
});

function caricaRecensioni() {
    fetch('/api/recensioni')
        .then(response => response.json())
        .then(data => {renderizzaRecensioni(data);})
        .catch(error => console.error("Errore nel caricamento delle recensioni:", error));
    }

function inviaRecensione() {
    const inputTesto = document.getElementById("testo");
    const stringaTesto = inputTesto.value.trim();

    if (stringaTesto === "") {
        alert("Il testo della recensione non può essere vuoto!");
        return;
    }

    const corpoOggetto = {
        recensione: stringaTesto
    };

    fetch('/api/recensioni', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(corpoOggetto)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Errore nel salvataggio");
        }
            return response.json();
        })
    .then(listaAggiornata => {
        inputTesto.value = "";
        renderizzaRecensioni(listaAggiornata);})
    .catch(error => {
        console.error("Errore nell'inserimento:", error);
        alert("Si è verificato un errore durante l'invio.");
    });
}

function renderizzaRecensioni(listaRecensioni) {
    const contenitoreCarousel = document.getElementById("carousel");
    contenitoreCarousel.innerHTML = "";
    if (listaRecensioni.length === 0) {
        contenitoreCarousel.innerHTML = "<p style='color:gray; width:100%; text-align:center;'>Nessuna recensione presente.</p>";
        return;
    }

    listaRecensioni.forEach(recensione => {
        const elementoCard = document.createElement("div");
        elementoCard.className = "card";

        elementoCard.innerHTML = `<p><strong>@${recensione.username}</strong></p><p>"${recensione.recensione}"</p>`;
        contenitoreCarousel.appendChild(elementoCard);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const datiStatistiche = ['${statisticheUtente}'];

    if (!datiStatistiche || datiStatistiche.length === 0) {
        document.getElementById('istogrammaAllenamenti').style.display = 'none';
        const fallbackMessage = document.createElement('p');
        fallbackMessage.innerText = "Non hai ancora completato alcun programma. Corri ad allenarti!";
        fallbackMessage.style.color = "#3e6f8e";
        fallbackMessage.style.fontStyle = "italic";
        fallbackMessage.style.padding = "20px";
        document.querySelector('.box-istogramma').appendChild(fallbackMessage);
        return;
    }

    const etichetteProgrammi = datiStatistiche.map(item => item.nomeProgramma); 
    const numeroEsecuzioni = datiStatistiche.map(item => item.conteggio); 

    const ctx = document.getElementById('istogrammaAllenamenti').getContext('2d');
    new Chart(ctx, {
        type: 'bar', 
        data: {
            labels: etichetteProgrammi,
            datasets: [{
                label: 'Numero di volte completato',
                data: numeroEsecuzioni,
                backgroundColor: '#3e6f8e',
                borderColor: '#3e6f8e',
                borderWidth: 2,
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, 
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1,
                        precision: 0
                    }
                }
            }
        }
    });
});