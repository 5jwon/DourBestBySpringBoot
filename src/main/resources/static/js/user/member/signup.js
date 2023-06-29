/* 주소 검색 자바 스크립트 */
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("adress").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("ditailadress").focus();
        }
    }).open();
}

// 모달
const contactmodal = document.querySelector(".contactmodal");
const btn = document.querySelector(".contactBtn");
const $span = document.querySelector(".close");


btn.addEventListener('click', openContactModal);
$span.addEventListener('click', closeContactModal);

function openContactModal() {
    contactmodal.style.display = "block";
    contactmodal.animate([{opacity: 0}, {opacity: 1}], {duration: 300, fill: "forwards"});
}

function closeContactModal() {
    contactmodal.animate([{opacity: 1}, {opacity: 0}], {duration: 300, fill: "forwards"}).onfinish = function() {
        contactmodal.style.display = "none";
        const $img = document.querySelector(".contact-imgdiv");
        if($img.hasChildNodes()){
            $img.replaceChildren();
        }
    };
}

function ReviewreadURL(obj) {

    let reader = new FileReader();
    if(!obj.files.length) {
        return;
    }
    reader.readAsDataURL(obj.files[0]);
    reader.onload = function (e) {
        let $div = $('<div>');
        $($div).css('width','120px');
        $($div).css('height','120px');
        $($div).css('padding','10px 10px 0px 0px');

        $('.reviewDiv').append($div);

        let img = $('<img />');
        $(img).attr('src', e.target.result);
        $(img).css('width','100%');
        $(img).css('height','100%');
        $($div).append(img);
    }
}



// <![CDATA[  <-- For SVG support
if ('WebSocket' in window) {
    (function () {
        function refreshCSS() {
            var sheets = [].slice.call(document.getElementsByTagName("link"));
            var head = document.getElementsByTagName("head")[0];
            for (var i = 0; i < sheets.length; ++i) {
                var elem = sheets[i];
                var parent = elem.parentElement || head;
                parent.removeChild(elem);
                var rel = elem.rel;
                if (elem.href && typeof rel != "string" || rel.length == 0 || rel.toLowerCase() == "stylesheet") {
                    var url = elem.href.replace(/(&|\?)_cacheOverride=\d+/, '');
                    elem.href = url + (url.indexOf('?') >= 0 ? '&' : '?') + '_cacheOverride=' + (new Date().valueOf());
                }
                parent.appendChild(elem);
            }
        }
        var protocol = window.location.protocol === 'http:' ? 'ws://' : 'wss://';
        var address = protocol + window.location.host + window.location.pathname + '/ws';
        var socket = new WebSocket(address);
        socket.onmessage = function (msg) {
            if (msg.data == 'reload') window.location.reload();
            else if (msg.data == 'refreshcss') refreshCSS();
        };
        if (sessionStorage && !sessionStorage.getItem('IsThisFirstTime_Log_From_LiveServer')) {
            console.log('Live reload enabled.');
            sessionStorage.setItem('IsThisFirstTime_Log_From_LiveServer', true);
        }
    })();
}
else {
    console.error('Upgrade your browser. This Browser is NOT supported WebSocket for Live-Reloading.');
}
// ]]>
