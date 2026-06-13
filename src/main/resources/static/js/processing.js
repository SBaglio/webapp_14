document.addEventListener("DOMContentLoaded", function() {
    caricaRecensioni();
});

function caricaRecensioni() {
    fetch('/getRecensioni')
        .then(response => response.json())
        .then(data => {
            data.sort(() => Math.random() - 0.5); // ordine randomizzato
            renderizzaRecensioni(data);
        })
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

    fetch('/inviaRecensione', {
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
        })
        .catch(error => {
            console.error("Errore nell'inserimento:", error);
            alert("Si è verificato un errore durante l'invio.");
        });
}

function renderizzaRecensioni(listaRecensioni) {
    const contenitoreInner = document.getElementById("carouselInner");
    contenitoreInner.innerHTML = "";

    if (listaRecensioni.length === 0) {
        contenitoreInner.innerHTML = `
            <div class="carousel-item active">
                <p style="color:gray; width:100%; text-align:center;">Nessuna recensione presente.</p>
            </div>`;
        return;
    }

    listaRecensioni.forEach((recensione, index) => {
        const elementoCard = document.createElement("div");
        elementoCard.className = "carousel-item" + (index === 0 ? " active" : "");

        elementoCard.innerHTML = `
            <div class="card p-4 text-center">
                <p><strong>@${escapeHtml(recensione.username)}</strong></p>
                <p>"${escapeHtml(recensione.recensione)}"</p>
            </div>`;
        contenitoreInner.appendChild(elementoCard);
    });
}

//se non ci sono recensioni
function escapeHtml(text) {
    const div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
}