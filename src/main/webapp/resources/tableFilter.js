function filterAll() {
    var input, filter, table, tr, td, cell, i, j;
    input = document.getElementById("parcelTableInputt");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
        // Hide the row initially.
        tr[i].style.display = "none";

        td = tr[i].getElementsByTagName("td");
        for (var j = 0; j < td.length; j++) {
            cell = tr[i].getElementsByTagName("td")[j];
            if (cell) {
                if (cell.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                    break;
                }
            }
        }
    }
}

function filterNumber() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("parcelTableInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) <= -1) {
            //     tr[i].style.display = "";
            // } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function filterRecipient() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("parcelTableInput2");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) <= -1) {
            //     tr[i].style.display = "";
            // } else {
                tr[i].style.display = "none";
            }
        }
    }
}


function filterDate() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("parcelTableInput3");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) <= -1) {
            //     tr[i].style.display = "";
            // } else {
                    tr[i].style.display = "none";
            }
        }
    }
}


function filterStatus() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("parcelTableInput4");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[3];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) <= -1) {
            //     tr[i].style.display = "";
            // } else {
                tr[i].style.display = "none";
            }
        }
    }
}


function filterPrice() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("parcelTableInput5");
    filter = input.value.toUpperCase();
    table = document.getElementById("parcelTable");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[4];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) <= -1) {
            //     tr[i].style.display = "";
            // } else {
                tr[i].style.display = "none";
            }
        }
    }
}


// function checkval(){
//     1==$("tbody tr:visible").length&&"No result found"==$("tbody tr:visible td").html()?
//         $("#rowcount").html("0"):
//         $("#rowcount").html($("tr:visible").length-1)}$(document).ready(
//     function(){
//         $("#rowcount").html($(".filterable tr").length-1),
//             $(".filterable .btn-filter").click(function(){
//                 var t=$(this).parents(".filterable"),e=t.find(".filters input"),
//                     l=t.find(".parcelsTable tbody");
//                 (e.val("").prop("disabled",!0),
//                     l.find(".no-result").remove(),
//                     l.find("tr").show()),
//                     $("#rowcount").html($(".filterable tr").length-1)}),
//             $(".filterable .filters input").keyup(function(t){if("9"!=(t.keyCode||t.which)){
//                 var e=$(this),l=e.val().toLowerCase(),
//                     n=e.parents(".filterable"),
//                     i=n.find(".filters th").index(e.parents("th")),
//                     r=n.find(".parcelsTable"),
//                     o=r.find("tbody tr"),d=o.filter(function(){
//                         return-1===$(this).find("td").eq(i).text().toLowerCase().indexOf(l)});
//                 r.find("tbody .no-result").remove(),o.show(),d.hide(),
//                 d.length===o.length&&r.find("tbody").prepend($('<tr class="no-result text-center">' +
//                     '<td colspan="'+r.find(".filters th").length+'">No result found</td></tr>'))}$("#rowcount").html($("tr:visible").length-1),checkval()})});
