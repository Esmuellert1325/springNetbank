document.addEventListener('DOMContentLoaded', () => {
    let emailOfUser = document.getElementById('emailOfUser').textContent;

    let trs = document.querySelectorAll('tr');

    trs.forEach(tr => {
        let email = tr.firstElementChild.textContent;
        let parent = tr.parentElement;

        if (parent.tagName == 'TBODY' && email == emailOfUser) {
            tr.style.color = 'green';
        }
        else if (parent.tagName == 'TBODY' && email != emailOfUser) {
            tr.style.color = 'red';
        }
    });

    function sortTableByColumn(table, column, asc = true) {
        const dirModifier = asc ? 1 : -1;
        const tBody = table.tBodies[0];
        const rows = Array.from(tBody.querySelectorAll("tr"));

        // Sort each row
        const sortedRows = rows.sort((a, b) => {
            const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
            const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim();

            return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);
        });

        // Remove all existing TRs from the table
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }

        // Re-add the newly sorted rows
        tBody.append(...sortedRows);

        // Remember how the column is currently sorted
        table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc"));
        table.querySelector(`th:nth-child(${column + 1})`).classList.toggle("th-sort-asc", asc);
        table.querySelector(`th:nth-child(${column + 1})`).classList.toggle("th-sort-desc", !asc);
    }

    let sortDateBtn = document.getElementById('dateOfTrans');
    sortDateBtn.onclick = () => {
        const tableElement = sortDateBtn.parentElement.parentElement.parentElement;
        const headerIndex = Array.prototype.indexOf.call(sortDateBtn.parentElement.children, sortDateBtn);
        const currentIsAscending = sortDateBtn.classList.contains("th-sort-asc");

        sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
    };

//    document.querySelectorAll(".table-sortable th").forEach(headerCell => {
//        headerCell.addEventListener("click", () => {
//            const tableElement = headerCell.parentElement.parentElement.parentElement;
//            const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);
//            const currentIsAscending = headerCell.classList.contains("th-sort-asc");
//
//            sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
//        });
//    });
});
