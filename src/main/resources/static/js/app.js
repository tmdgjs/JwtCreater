$(document).ready(() => {
    fnJwtList();

})

let state = {
    intPayloadSize : 1,
    intPayloadMaxSize : 10,
    intPayloadAddCount : 1
}

const fnDefaultAjax = (type, url, data, successFunction, errorFunction) => {

    $.ajax({
        type : type,
        contentType: 'application/json',
        url  : url,
        //async : false,
        data : JSON.stringify(data),
        success : res => {
            successFunction(res)
        },
        error : err => {
            errorFunction(err)
        },
    })
}

const fnObjectAdd = () => {

    if(state.intPayloadSize + 1 > state.intPayloadMaxSize){
        alert("Payload의 데이터 개수는 최대 30개입니다.")
        return;
    }

    let objPayloadData = `<li class='payload-item' id=payload`+ state.intPayloadAddCount +`>
                            <input class="key" placeholder='Key' /> <span class='colon'>:</span>
                            <input class="value" placeholder='Value'/>
                            <button onclick='fnObjectRemove(this)'>➖</button>
                        </li>`;

    $("#payloadDataList").append(objPayloadData);

    state.intPayloadSize++;
    state.intPayloadAddCount++;
}

const fnObjectRemove = (item) => {

    let parentID = "#" + $(item).parent().attr('id');

    if(parentID === "#payload0"){
        alert("이 항목은 Remove할 수 없습니다.")
        return;
    }

    $(parentID).remove();
    state.intPayloadSize--;
}

const fnJwtCreater = () => {

    let algorithm = $("#algorithm").val();
    let secretKey = $("#secretKey").val();
    let payload   = fnPayload();

    let objReq = new Object();

    objReq.algo = algorithm;
    objReq.secretKey = secretKey;
    objReq.data = payload;

    fnDefaultAjax("post","/jwts", objReq, fnJwtCreateSuccess, fnJwtCreateError)
}

const fnPayload = () => {

    let obj = new Map();

    for(let i=0; i < state.intPayloadSize; i++){
        let key   = $(".payload-item:eq("+i+")").children(".key").val()
        let value = $(".payload-item:eq("+i+")").children(".value").val()

        obj.set(key, value);
    }

    return Object.fromEntries(obj);
}

const fnJwtSave = () => {

    let token = $("#createdToken").html();

    let objReq = new Object();
    objReq.token = token;

    fnDefaultAjax("post","/jwts/stores", objReq, fnJwtSaveSuccess, fnJwtSaveError)
}

const fnRedisItemOnclick = (item) => {
    fnGetJwt(item);
}

const fnJwtList = () => {

    $("#jwtList").empty();

    fnDefaultAjax("get","/jwts", "", fnJwtListSuccess, fnJwtListError)
}

const fnGetJwt = (item) => {

    let id = $(item).attr("id");
    
    fnDefaultAjax("get","/jwts/"+id, "", fnGetJwtSuccess, fnGetJwtError)
}

const fnCopyBtn = () => {

    let token = $("#jwtHeader").html( ) + "." +  $("#jwtPayload").html() + "." + $("#jwtSignature").html()
    copyToClipboard(token);
}

const copyToClipboard = (value) => {

    var t = document.createElement("textarea");
    document.body.appendChild(t);
    t.value = value;
    t.select();
    document.execCommand('copy');
    document.body.removeChild(t);

    alert("복사 성공")
}