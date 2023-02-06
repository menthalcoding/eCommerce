function addToBasket(productId, quantity) {


    if (quantity == 0)
        return;
    else if (quantity == null)
        quantity = 1;

    var item = {
        "ProductId": productId,
        "Quantity": quantity
    };
    let response = fetch('/Basket/AddToBasket', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        },
        body: JSON.stringify(item)
    }).then((response) => response.json())
        .then((responseData) => {
            document.getElementById("basketLink").innerText = "(" + responseData + ")";
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again.");
        });
}

function getBasketItemCount() {

    var basketLink = document.getElementById('basketLink');
    if (basketLink != null) {
        let response = fetch('/Basket/BasketItemCount', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json',
                'pragma': 'no-cache',
                'cache-control': 'no-cache'
            }
        }).then((response) => response.json())
            .then((responseData) => {
                if (responseData != null && responseData > 0) {
                    basketLink.innerHTML = "(" + responseData + ")";
                }
                else {
                    basketLink.innerHTML = "(0)";
                }
            })
            .catch(error => {
                alert("An error occurred.\nPlease try again.");
            });
    }
}

function changeQuantity(productId, quantity) {

    var item = {
        "ProductId": productId,
        "Quantity": quantity
    };
    let response = fetch('/Basket/ChangeQuantity', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        },
        body: JSON.stringify(item)
    }).then((response) => response.json())
        .then((responseData) => {
            console.log("responseData: " + parseInt(responseData));

            document.getElementById("itemPrice_" + productId).innerText = "$" + responseData * quantity;

            totalAmount();
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again.");
        });
}

function totalAmount() {

    let response = fetch('/Basket/TotalAmount', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        }
    }).then((response) => response.json())
        .then((responseData) => {
            document.getElementById("productAmount").innerText = "$" + responseData;
            document.getElementById("totalAmount").innerText = "$" + responseData;
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again.");
        });
}

function removeCartItem(productId) {

    let response = fetch('/Basket/RemoveCartItem', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        },
        body: JSON.stringify(productId)
    }).then((response) => response.json())
        .then((responseData) => {
            location.reload();
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again.");
        });
}

// ADDRESS

function getAddresses() {
    var selUserAddress = document.getElementById('addressNames');
    selUserAddress.innerHTML = '';
    let response = fetch('/Address', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        }
    }).then((response) => response.json())
        .then((responseData) => {
            //alert(JSON.stringify(responseData));
            var itemSelected = false;
            for (var k in responseData) {
                var option = document.createElement("option");
                option.value = responseData[k].addressId;
                option.text = responseData[k].name;
                if (responseData[k].primary) {
                    option.setAttribute('selected', 'selected');
                    document.getElementById('addressDetail').innerHTML = responseData[k].detail + " " + responseData[k].city + "/" + responseData[k].country;
                    itemSelected = true;
                }
                selUserAddress.add(option);

                if (!itemSelected) {
                    document.getElementById('addressDetail').innerHTML = "";
                }
            }
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again." + error);
        });
}

function getAddress(addressId) {
    let response = fetch('/Address/Details?AddressId=' + addressId, {
        method: 'GET',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        }
    }).then((response) => response.json())
        .then((responseData) => {
            document.getElementById('addressDetail').innerHTML = responseData.detail + " " + responseData.city + "/" + responseData.country;
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again." + error);
        });
}

function saveAddress() {
    var name = document.getElementById("newAddressName");
    var country = document.getElementById("newAddressCountry");
    var city = document.getElementById("newAddressCity");
    var zipCode = document.getElementById("newAddressZip");
    var detail = document.getElementById("newAddressDetail");
    var isDefault = document.getElementById("newAddressDefault");
    var model = {
        "Name": name.value,
        "GPS": "",
        "Country": country.value,
        "City": city.value,
        "ZipCode": zipCode.value,
        "CountryCode": "",
        "Detail": detail.value,
        "Primary": isDefault.checked
    };
    let response = fetch('/Address/Create', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        },
        body: JSON.stringify(model)
    }).then((response) => response.json())
        .then((responseData) => {
            getAddresses();

            name.value = "";
            country.value = "";
            city.value = "";
            zipCode.value = "";
            detail.value = "";
            isDefault.checked = false;

            $('#modalAddress').modal('hide');
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again.");
        });
}

function removeAddress() {

    if (!confirm("Are you sure?")) {
        return;
    }

    var selUserAddress = document.getElementById('addressNames');
    let response = fetch('/Address/Delete?AddressId=' + selUserAddress.value, {
        method: 'GET',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json',
            'pragma': 'no-cache',
            'cache-control': 'no-cache'
        }
    }).then((response) => response.json())
        .then((responseData) => {
            getAddresses();
        })
        .catch(error => {
            alert("An error occurred.\nPlease try again." + error);
        });
}


function checkStatus() {
    var url_string = window.location.href;
    var url = new URL(url_string.toLowerCase());
    var status = url.searchParams.get("status");
    if (status != null) {
        var arrStatus = status.split(',');
        var checkboxes = document.querySelectorAll("input[type='checkbox'][name='cbStatus']");
        for (var c = 0; c < checkboxes.length; c++) {
            for (var s = 0; s < arrStatus.length; s++) {
                if (checkboxes[c].value == arrStatus[s])
                    checkboxes[c].checked = true;
            }
        }
    }
}

function changeStatus(ctrl) {
    var url_string = window.location.href;
    var url = new URL(url_string.toLowerCase());
    var status = url.searchParams.get("status");
    if (ctrl.checked) {
        if (status != null) {
            if (status.indexOf(ctrl.value) < 0) {
                status += "," + ctrl.value;
            }
        }
        else {
            status = ctrl.value;
        }
    }
    else {
        if (status != null) {
            var arrStatus = status.split(',');
            for (var s = 0; s < arrStatus.length; s++) {
                if (arrStatus[s] == ctrl.value) {
                    arrStatus.splice(s, 1);
                }
            }
            if (arrStatus.length > 0)
                status = arrStatus.join(',');
            else
                status = null;
        }
    }

    var categoryId = url.searchParams.get("categoryid");
    var sort = url.searchParams.get("sort");
    var direction = url.searchParams.get("orderdirection");
    var params = "";
    params = (categoryId != null ? categoryId : "");
    params += (status != null ? (params != "" ? "&" : "") + "status=" + status : "");
    params += (sort != null ? (params != "" ? "&" : "") + "sort=" + sort : "");
    params += (direction != null ? (params != "" ? "&" : "") + "orderDirection=" + direction : "");
    window.location.href = window.location.pathname + (params != null ? "?" : "") + params;
}

function changeSortByProduct() {
    var url_string = window.location.href;
    var url = new URL(url_string.toLowerCase());
    var selOrder = document.getElementById('selOrder');
    if (selOrder != null) {
        var params = "";
        params = (url.searchParams.get("categoryid") != null ? "categoryId=" + url.searchParams.get("categoryid") : "");
        params += (url.searchParams.get("status") != null ? (params != "" ? "&" : "") + "status=" + url.searchParams.get("status") : "");
        params += (selOrder.value != null ? (params != "" ? "&" : "") + "sort=" + selOrder.value : "");
        params += (selOrder.options[selOrder.selectedIndex].getAttribute("direction") != null ? (params != "" ? "&" : "") + "orderDirection=" + selOrder.options[selOrder.selectedIndex].getAttribute("direction") : "");
        window.location.href = window.location.pathname + "?" + params;
    }
}

function imageZoom(imgID, resultID) {
    var img, lens, result, cx, cy;
    img = document.getElementById(imgID);
    result = document.getElementById(resultID);
    /*create lens:*/
    lens = document.createElement("DIV");
    lens.setAttribute("class", "img-zoom-lens");
    /*insert lens:*/
    img.parentElement.insertBefore(lens, img);
    /*calculate the ratio between result DIV and lens:*/
    cx = result.offsetWidth / lens.offsetWidth;
    cy = result.offsetHeight / lens.offsetHeight;
    /*set background properties for the result DIV:*/
    result.style.backgroundImage = "url('" + img.src + "')";
    result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
    /*execute a function when someone moves the cursor over the image, or the lens:*/
    lens.addEventListener("mousemove", moveLens);
    img.addEventListener("mousemove", moveLens);
    /*and also for touch screens:*/
    lens.addEventListener("touchmove", moveLens);
    img.addEventListener("touchmove", moveLens);

    function moveLens(e) {
        var pos, x, y;
        /*prevent any other actions that may occur when moving over the image:*/
        e.preventDefault();
        /*get the cursor's x and y positions:*/
        pos = getCursorPos(e);
        /*calculate the position of the lens:*/
        x = pos.x - (lens.offsetWidth / 2);
        y = pos.y - (lens.offsetHeight / 2);
        /*prevent the lens from being positioned outside the image:*/
        if (x > img.width - lens.offsetWidth) { x = img.width - lens.offsetWidth; }
        if (x < 0) { x = 0; }
        if (y > img.height - lens.offsetHeight) { y = img.height - lens.offsetHeight; }
        if (y < 0) { y = 0; }
        /*set the position of the lens:*/
        lens.style.left = x + "px";
        lens.style.top = y + "px";
        /*display what the lens "sees":*/
        result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
    }
    function getCursorPos(e) {
        var a, x = 0, y = 0;
        e = e || window.event;
        /*get the x and y positions of the image:*/
        a = img.getBoundingClientRect();
        /*calculate the cursor's x and y coordinates, relative to the image:*/
        x = e.pageX - a.left;
        y = e.pageY - a.top;
        /*consider any page scrolling:*/
        x = x - window.pageXOffset;
        y = y - window.pageYOffset;
        return { x: x, y: y };
    }
}
