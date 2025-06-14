document.addEventListener('DOMContentLoaded', function () {
    const checkboxes = document.querySelectorAll('.produto-checkbox');
    const tabelaCorpo = document.querySelector('#tabela-produtos-selecionados tbody');
    const totalElement = document.getElementById('total-pedido');

    function atualizarTabelaEtotal() {
        let total = 0;
        tabelaCorpo.innerHTML = '';

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                const nome = checkbox.dataset.nome;
                const preco = parseFloat(checkbox.dataset.preco);

                total += preco;

                const row = tabelaCorpo.insertRow();
                const cellNome = row.insertCell(0);
                const cellPreco = row.insertCell(1);

                cellNome.textContent = nome;
                cellPreco.textContent = 'R$ ' + preco.toFixed(2).replace('.', ',');
            }
        });

        totalElement.textContent = 'R$ ' + total.toFixed(2).replace('.', ',');
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', atualizarTabelaEtotal);
    });


    atualizarTabelaEtotal();
});