<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.List"%>
<%@ page import="com.foodiehub.model.Cart"%>
<%@ page import="com.foodiehub.model.User"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | Checkout</title>

<link rel="icon" type="image/x-icon" href="favicon.ico">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">

</head>
<body>

<%
User loggedUser = (User) session.getAttribute("user");
List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
Double subtotal = (Double) request.getAttribute("subtotal");
Double deliveryFee = (Double) request.getAttribute("deliveryFee");
Double gst = (Double) request.getAttribute("gst");
Double total = (Double) request.getAttribute("total");
String savedAddress = loggedUser.getAddress();
%>

<nav class="navbar">
    <div class="logo">🍽 Zestora</div>
    <div class="nav-links">
        <a href="restaurants">Home</a>
        <a href="restaurants">Restaurants</a>
        <a href="cart">Cart</a>
        <a href="orders">My Orders</a>
<% if(loggedUser != null){ %>
        <span class="nav-welcome">Hi, <%=loggedUser.getUserName()%></span>
<% } %>
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="page-shell">
<div class="menu-page checkout-wrap">

<h1 class="menu-title">Checkout</h1>

<!-- STEP INDICATOR -->
<div class="checkout-stepper">
    <div class="step active" id="stepper-1">
        <span class="step-num">1</span>
        <span class="step-label">Details</span>
    </div>
    <div class="step-line" id="line-1"></div>
    <div class="step" id="stepper-2">
        <span class="step-num">2</span>
        <span class="step-label">Order Summary</span>
    </div>
    <div class="step-line" id="line-2"></div>
    <div class="step" id="stepper-3">
        <span class="step-num">3</span>
        <span class="step-label">Payment</span>
    </div>
</div>

<!-- STEP 1: CONTACT + ADDRESS -->
<div class="checkout-panel" id="panel-1">

    <div class="checkout-block">
        <h3>Contact Details</h3>

        <div class="form-group">
            <label>Receiver's Name</label>
            <input type="text" id="contactName" name="contactName" form="checkoutForm"
                   value="<%=loggedUser.getUserName()%>" required>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <input type="tel" id="contactPhone" name="contactPhone" form="checkoutForm"
                   pattern="[0-9]{10}" maxlength="10"
                   placeholder="10-digit mobile number" required>
        </div>
    </div>

    <div class="checkout-block">
        <h3>Delivery Address</h3>

        <label class="address-choice">
            <input type="radio" name="addressChoice" value="saved" checked
                   onclick="toggleAddress('saved')">
            <span>Use my saved address</span>
        </label>

        <label class="address-choice">
            <input type="radio" name="addressChoice" value="different"
                   onclick="toggleAddress('different')">
            <span>Deliver to a different address</span>
        </label>

        <textarea id="deliveryAddressField" name="deliveryAddress" form="checkoutForm"
                  class="address-textarea" rows="3" readonly><%=savedAddress%></textarea>
    </div>

    <div class="step-nav">
        <a href="cart" class="back-btn">← Back to Cart</a>
        <button type="button" class="next-btn" onclick="goToStep(2)">Continue →</button>
    </div>
</div>

<!-- STEP 2: ORDER SUMMARY -->
<div class="checkout-panel" id="panel-2" style="display:none;">
    <div class="checkout-block">
        <h3>Order Items</h3>
<%
if(cartItems != null){
    for(Cart c : cartItems){
%>
        <div class="order-item-row">
            <span><%=c.getItemName()%> &times; <%=c.getQuantity()%></span>
            <span>₹<%=(int)(c.getPrice()*c.getQuantity())%></span>
        </div>
<%
    }
}
%>
    </div>

    <div class="checkout-block">
        <div class="summary-row">
            <span>Subtotal</span>
            <span>₹<%=(int)(double)subtotal%></span>
        </div>

        <div class="summary-row">
            <span>Delivery Fee</span>
<% if((double)deliveryFee == 0){ %>
            <span class="free-delivery-tag">FREE</span>
<% } else { %>
            <span>₹<%=(int)(double)deliveryFee%></span>
<% } %>
        </div>

        <div class="summary-row">
            <span>GST (5%)</span>
            <span>₹<%=(int)(double)gst%></span>
        </div>

        <div class="summary-row total">
            <span>Total</span>
            <span>₹<%=(int)(double)total%></span>
        </div>
    </div>

    <div class="step-nav">
        <button type="button" class="back-btn" onclick="goToStep(1)">← Back</button>
        <button type="button" class="next-btn" onclick="goToStep(3)">Continue →</button>
    </div>
</div>

<!-- STEP 3: PAYMENT -->
<div class="checkout-panel" id="panel-3" style="display:none;">
    <div class="checkout-block">
        <h3>Select Payment Method</h3>

        <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm">

            <label class="payment-option">
            <input type="radio" name="paymentMethod" value="Cash" checked onclick="showPaymentDetail('Cash'); updateOrderButtonLabel('Cash')">
                <span class="payment-icon">
                    <svg viewBox="0 0 24 24" width="26" height="26" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="2" y="6" width="20" height="12" rx="2"/>
                        <circle cx="12" cy="12" r="3"/>
                        <path d="M6 6v.01M18 18v.01"/>
                    </svg>
                </span>
                <span class="payment-text">Cash on Delivery</span>
            </label>

            <label class="payment-option">
            <input type="radio" name="paymentMethod" value="UPI" onclick="showPaymentDetail('UPI'); updateOrderButtonLabel('UPI')">
                <span class="payment-icon">
                    <svg viewBox="0 0 24 24" width="26" height="26" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="5" y="2" width="14" height="20" rx="2"/>
                        <path d="M9 6h6M9 10h6M9 14h3"/>
                        <circle cx="12" cy="18" r="1" fill="currentColor" stroke="none"/>
                    </svg>
                </span>
                <span class="payment-text">UPI</span>
            </label>

            <label class="payment-option">
            <input type="radio" name="paymentMethod" value="Card" onclick="showPaymentDetail('Card'); updateOrderButtonLabel('Card')">
                <span class="payment-icon">
                    <svg viewBox="0 0 24 24" width="26" height="26" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="2" y="5" width="20" height="14" rx="2"/>
                        <path d="M2 10h20"/>
                        <path d="M6 15h4"/>
                    </svg>
                </span>
                <span class="payment-text">Credit / Debit Card</span>
            </label>

        </form>

        <!-- CASH DETAIL -->
        <div class="payment-detail" id="detail-Cash">
            <p class="payment-detail-text">💵 Pay in cash when your order arrives at the door.</p>
        </div>

        <!-- UPI DETAIL -->
        <div class="payment-detail" id="detail-UPI" style="display:none;">
            <div class="qr-box">
                <img src="images/upi_qr_demo.png" alt="Scan to pay via UPI" class="qr-image">
                <p class="qr-label">Scan with any UPI app</p>
                <div class="upi-id-row">
                    <span>UPI ID:</span>
                    <span class="upi-id-value">zestora.demo@fakebank</span>
                </div>
            </div>
            <p class="demo-note">🔒 Demo checkout — no real payment is processed.</p>
        </div>

        <!-- CARD DETAIL -->
        <div class="payment-detail" id="detail-Card" style="display:none;">
            <div class="form-group">
                <label>Card Number</label>
                <input type="text" id="cardNumber" placeholder="1234 5678 9012 3456"
                       maxlength="19" oninput="formatCardNumber(this)">
            </div>

            <div class="card-detail-row">
                <div class="form-group">
                    <label>Expiry</label>
                    <input type="text" placeholder="MM/YY" maxlength="5" oninput="formatExpiry(this)">
                </div>

                <div class="form-group">
                    <label>CVV</label>
                    <input type="password" placeholder="•••" maxlength="3">
                </div>
            </div>

            <div class="form-group">
                <label>Name on Card</label>
                <input type="text" placeholder="As printed on card">
            </div>

            <p class="demo-note">🔒 Demo checkout — no real card details are stored or charged.</p>
        </div>

        <div class="payment-total-line">
            <span>Total to Pay</span>
            <span>₹<%=(int)(double)total%></span>
        </div>
    </div>

    <div class="step-nav">
        <button type="button" class="back-btn" onclick="goToStep(2)">← Back</button>
<button type="button" id="placeOrderBtn" class="place-order-btn" onclick="handlePlaceOrder()">Place Order</button>
    </div>
</div>

</div>
</div>
<div class="payment-overlay" id="paymentOverlay" style="display:none;">
    <div class="payment-overlay-card">

        <div class="payment-spinner" id="paymentSpinner"></div>

        <div class="success-checkmark" id="successCheckmark" style="display:none;">
            <svg viewBox="0 0 52 52" width="70" height="70">
                <circle class="checkmark-circle" cx="26" cy="26" r="24" fill="none"/>
                <path class="checkmark-check" fill="none" d="M14 27l7 7 16-16"/>
            </svg>
        </div>

        <p class="payment-status-text" id="paymentStatusText">Processing your payment...</p>
        <p class="payment-amount-text" id="paymentAmountText" style="display:none;">₹<%=(int)(double)total%> paid successfully</p>
        <p class="payment-txn-text" id="paymentTxnText" style="display:none;"></p>

    </div>
</div>
<script>
var savedAddressText = <%= "\"" + savedAddress.replace("\"", "\\\"").replace("\n"," ") + "\"" %>;

function toggleAddress(choice){
    var field = document.getElementById('deliveryAddressField');

    if(choice === 'saved'){
        field.value = savedAddressText;
        field.readOnly = true;
    } else {
        field.value = '';
        field.readOnly = false;
        field.focus();
    }
}

function goToStep(stepNum){
    if(stepNum > 1){
        var name = document.getElementById('contactName');
        var phone = document.getElementById('contactPhone');
        var addressField = document.getElementById('deliveryAddressField');

        if(!name.value.trim()){
            alert('Please enter the receiver\'s name.');
            return;
        }
        if(!/^[0-9]{10}$/.test(phone.value.trim())){
            alert('Please enter a valid 10-digit phone number.');
            return;
        }
        if(!addressField.value.trim()){
            alert('Please enter a delivery address.');
            return;
        }
    }

    for(var i = 1; i <= 3; i++){
        document.getElementById('panel-' + i).style.display = (i === stepNum) ? 'block' : 'none';

        var stepperEl = document.getElementById('stepper-' + i);
        stepperEl.classList.remove('active', 'completed');

        if(i === stepNum){
            stepperEl.classList.add('active');
        } else if(i < stepNum){
            stepperEl.classList.add('completed');
        }
    }

    document.getElementById('line-1').classList.toggle('completed', stepNum > 1);
    document.getElementById('line-2').classList.toggle('completed', stepNum > 2);

    window.scrollTo({top: 0, behavior: 'smooth'});
}
function showPaymentDetail(method){
    ['Cash', 'UPI', 'Card'].forEach(function(m){
        document.getElementById('detail-' + m).style.display = (m === method) ? 'block' : 'none';
    });
}

function formatCardNumber(input){
    var digits = input.value.replace(/\D/g, '').substring(0, 16);
    input.value = digits.replace(/(.{4})/g, '$1 ').trim();
}

function formatExpiry(input){
    var digits = input.value.replace(/\D/g, '').substring(0, 4);
    if(digits.length > 2){
        input.value = digits.substring(0,2) + '/' + digits.substring(2);
    } else {
        input.value = digits;
    }
}
function updateOrderButtonLabel(method){
    var btn = document.getElementById('placeOrderBtn');
    if(method === 'Cash'){
        btn.textContent = 'Place Order';
    } else {
        btn.textContent = 'Pay ₹<%=(int)(double)total%> Now';
    }
}

function handlePlaceOrder(){
    var method = document.querySelector('input[name="paymentMethod"]:checked').value;

    if(method === 'Cash'){
        document.getElementById('checkoutForm').submit();
    } else {
        simulatePayment();
    }
}

function simulatePayment(){
    var overlay = document.getElementById('paymentOverlay');
    var spinner = document.getElementById('paymentSpinner');
    var checkmark = document.getElementById('successCheckmark');
    var text = document.getElementById('paymentStatusText');
    var amountText = document.getElementById('paymentAmountText');
    var txnText = document.getElementById('paymentTxnText');

    overlay.style.display = 'flex';
    spinner.style.display = 'block';
    checkmark.style.display = 'none';
    text.textContent = 'Processing your payment...';
    amountText.style.display = 'none';
    txnText.style.display = 'none';

    setTimeout(function(){
        spinner.style.display = 'none';
        checkmark.style.display = 'block';
        text.textContent = 'Payment Successful!';
        amountText.style.display = 'block';

        var txnId = 'TXN' + Date.now().toString().slice(-10);
        txnText.textContent = 'Ref ID: ' + txnId;
        txnText.style.display = 'block';

        setTimeout(function(){
            document.getElementById('checkoutForm').submit();
        }, 1400);

    }, 1600);
}
</script>

</body>
</html>