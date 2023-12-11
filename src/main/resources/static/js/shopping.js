function addItem() {
    var itemName = $('#itemName').val();
    $.ajax({
        type: 'POST',
        url: '/api/shopping-items',
        contentType: 'application/json',
        data: JSON.stringify({ name: itemName }),
        success: function (data) {
            fetchShoppingList();
            $('#itemName').val('');
        },
        error: function () {
            alert('Error adding item.');
        }
    });
}

function fetchShoppingList() {
    $.get('/api/shopping-items', function (data) {
        var shoppingList = $('#shoppingList');
        shoppingList.empty();
        data.forEach(function (item) {
            var listItem = $('<li>').text(item.name);
            if (item.purchased) {
                listItem.addClass('purchased');
            }
            listItem.append(createPurchaseButton(item.id));
            listItem.append(createDeleteButton(item.id));
            shoppingList.append(listItem);
        });
    });
}

function createPurchaseButton(itemId) {
    return $('<button>')
        .text('Purchase')
        .click(function () {
            markAsPurchased(itemId);
        });
}

function createDeleteButton(itemId) {
    return $('<button>')
        .text('Delete')
        .click(function () {
            deleteItem(itemId);
        });
}

function markAsPurchased(itemId) {
    $.ajax({
        type: 'PUT',
        url: '/api/shopping-items/' + itemId + '/purchase',
        success: function () {
            fetchShoppingList();
        },
        error: function () {
            alert('Error marking item as purchased.');
        }
    });
}

function deleteItem(itemId) {
    $.ajax({
        type: 'DELETE',
        url: '/api/shopping-items/' + itemId,
        success: function () {
            fetchShoppingList();
        },
        error: function () {
            alert('Error deleting item.');
        }
    });
}

// Fetch shopping list on page load
$(document).ready(function () {
    fetchShoppingList();
});