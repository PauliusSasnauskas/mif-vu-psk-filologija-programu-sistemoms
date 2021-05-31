const table = document.getElementById("parcelTable");
const rows = Array.from(table.getElementsByTagName("tr"));
rows.splice(0, 1); // remove filter row

const items = [];

// Get current row data
rows.forEach((tr) => {
    const tds = Array.from(tr.children);
    const itemData = [];
    tds.forEach((td) => {
        itemData.push(td.innerText);
    });
    items.push({tr: tr, itemData: itemData});
});

// Filter by specific column
const filterBy = (column, filterText) => {
    items.forEach((item) => {
        const text = item.itemData[column].toLowerCase();
        if (!text.includes(filterText)){
            item.tr.style.display = "none";
        }else{
            item.tr.style.display = "";
        }
    })
};

// Convenient function which returns event for each of the columns
const filterByEvent = (column) => {
    return (e) => {
        filterBy(column-1, e.target.value.toLowerCase());
    }
};

document.getElementById("parcelTableInput1").addEventListener("keyup", filterByEvent(1));
document.getElementById("parcelTableInput2").addEventListener("keyup", filterByEvent(2));
document.getElementById("parcelTableInput3").addEventListener("keyup", filterByEvent(3));
document.getElementById("parcelTableInput4").addEventListener("keyup", filterByEvent(4));
document.getElementById("parcelTableInput5").addEventListener("keyup", filterByEvent(5));

// Filter by all columns
const filterByAll = (filterText) => {
    items.forEach((item) => {
        let shouldHide = true;
        for (let column of item.itemData){
            if (column.toLowerCase().includes(filterText)){
                shouldHide = false;
                break;
            }
        }

        if (shouldHide){
            item.tr.style.display = "none";
        }else{
            item.tr.style.display = "";
        }
    })
};

document.getElementById("parcelTableInputAll").addEventListener("keyup", (e) => {
    filterByAll(e.target.value.toLowerCase());
});