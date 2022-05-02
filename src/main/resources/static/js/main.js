$(document).ready(function () {
    allBooks();

    $('#searchButton').click(showTabl);
    $('#allButton').click(allBooks);

    function allBooks() {
        $.ajax({
            type: 'GET',
            url: '/get-books',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                $('#tableBook').html(searchResults(res));

            },
            error: function (response) {
                console.log(response);
            }
        })
    }

    function showTabl() {
        $.ajax({
            type: 'GET',
            url: '/search-books',
            dataType: 'json',
            data: {
                getBy: $("#searchInput").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                $("#searchInput").val("");
                $('#tableBook').html(searchResults(res));
            },
            error: function (response) {
                console.log(response);
            }
        })
    }

    function searchResults(books){
        let tabl = "<table class='table table-hover'>"+
            "<thead class='table-light'>"+
            "<th>Isbn</th>"+
            "<th>Title</th>"+
            "<th>Author</th>"+
            "<th></th>"+
            "<th></th>"+
            "</thead>"+
            "<tbody>";

        for(let i=0; i<books.length; i++){

            tabl +=  "<tr>"+
                "<td>"+books[i].isbn+"</td>"+
                "<td>"+books[i].title+"</td>"+
                "<td>"+books[i].author+"</td>"+
                "<td ><a href=/book/"+books[i].isbn+">View</a></td>"+
                "<td><a href='/add-fav/"+books[i].isbn+"' class='btn btn-outline-dark'><i class='fas fa-star bg-warning'></i>Add to fav</a></td>"+
                "</tr>";
        }

        tabl += "</tbody></table>";
        return tabl;
    }

    $('#addButton').click(function (event){
        event.preventDefault();
        let book = {
            isbn: $("#isbn").val(),
            title: $("#title").val(),
            author: $("#author").val()
        }
        console.log(book);

        $.ajax({
            type: 'POST',
            url: '/book',

            data: JSON.stringify(book),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                console.log(response);
                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
                window.location='/'
            },
            error: function (response) {
                alert(response.responseText);
                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
                console.log(response.responseText);
            }
        })
    });
});