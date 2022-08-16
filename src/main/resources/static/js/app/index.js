var post = {
    init: function () {
        var _this = this;
        $('#btn-save-post').on('click', function () {
            _this.save();
        });
        $('#btn-update-post').on('click', function () {
            _this.update();
        });
        $('#btn-delete-post').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            categoryName: $('#categoryName').val()
            // author: $('#author').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/write',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 저장되었습니다.');
            window.location.href = '/posts?page=1&size=5';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            categoryName: $('#categoryName').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PATCH',
            url: '/api/post/' + id + '/edit',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/posts?page=1&size=5';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/post/' + id + '/delete',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/posts?page=1&size=5';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

var category = {
    init: function () {
        var _this = this;
        $('#btn-save-category').on('click', function () {
            _this.save_category();
        });
        $('#btn-update-category').on('click', function () {
            _this.update_category();
        });
        $('#btn-delete-category').on('click', function () {
            _this.delete_category();
        });
    },
    save_category: function () {
        var data = {
            name: $('#name').val()
        };

        $.ajax({
            type: 'POST',
            url: '/category/add',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('카테고리가 저장되었습니다.');
            window.location.href = '/categories';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update_category: function () {
        var data = {
            name: $('#name').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PATCH',
            url: '/category/' + id + '/edit',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('카테고리가 수정되었습니다.');
            window.location.href = '/categories';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete_category: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/category/' + id + '/delete',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('카테고리가 삭제되었습니다.');
            window.location.href = '/categories';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

post.init();
category.init();