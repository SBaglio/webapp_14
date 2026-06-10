
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
        renderizzaRecensioni(listaAggiornata);
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