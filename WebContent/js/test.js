/**
 *
 */
$(function () {

    $('#Hidetext1').hide();
    $('#Hidetext2').hide();
    $('#Hidetext3').hide();
    $('#Hidebutton').hide();

    console.log("読み込みOK！");

    $('#show').click(function () {
        console.log('クリックされました！');
        $('#Hidetext1').show();
    })

    $('#btnHide1').click(function () {
        console.log('クリックされました！');
        $('#Hidetext1').hide();
        $('#Hidetext2').show();
    })

    $('#btnHide2').click(function () {
        console.log('クリックされました！');
        $('#Hidetext2').hide();
        $('#Hidetext3').show();
        $('#Hidebutton').show();
    })

    // $('#btnHide2').click(function () {
    //     console.log('クリックされました！');
    //     $('#Hidetext2').show();
    // })

    // $('#btnHide3').click(function () {
    //     console.log('クリックされました！');
    //     $('#Hidetext3').hide();
    // })

});