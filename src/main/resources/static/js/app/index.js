let post = {
    init: function () {
        let _this = this;
        $('#btn-save-post').on('click', function () {
            _this.save();
        });
        $('#btn-update-post').on('click', function () {
            _this.update();
        });
        $('#btn-delete-post').on('click', function () {
            _this.delete();
        });
        $('#btn-search-post').on('click', function () {
            _this.searchPost();
        });
    },
    save: function () {
        let data = {
            title: $('#title').val(),
            content: $('#content').val(),
            categoryName: $('#categoryName').val()
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
        let data = {
            title: $('#title').val(),
            content: $('#content').val(),
            categoryName: $('#categoryName').val()
        };

        let id = $('#id').val();

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
        let id = $('#id').val();

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
    searchPost: function () {
        let type = $('#type').val();
        let keyword = $('#keyword').val();

        $.ajax({
            type: 'GET',
            url: '/search?type=' + type + '&keyword=' + keyword
        }).done(
            function () {
                window.location.href = '/search?type=' + type + '&keyword=' + keyword;
            }
        );
    },
}

let category = {
    init: function () {
        let _this = this;
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
        let data = {
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
        let data = {
            name: $('#name').val()
        };

        let id = $('#id').val();

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
        let id = $('#id').val();

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

let users = {
    init: function () {
        let _this = this;
        $('#btn-role-update').on('click', function () {
            _this.role_update();
        });
    },

    role_update: function () {
        // 아래 형태의 JSON을 만드는건데, 여러 data를 받아와서 처리할 순 없을까? JSON List를 만들수 없나?
        let data = {
            userEmail: $('#userEmail').val(),
            userRole: $('#userRole').val(),
        };

        // 순수 javascript -> vanilla javascript
        let userEmails = document.getElementsByName("userEmail");
        let userRoles = document.getElementsByName("userRole");


        // JQuery -> 무거우니 사용을 지양한다.
        // let userEmails = $('[name="userEmail"]');
        // let userRoles = $('[name="userRole"] :selected');

        let dataList = [];

        for (let i = 0; i < userEmails.length; i++) {
            dataList.push({
                userEmail: userEmails[i].innerText,
                userRole: userRoles[i].options[userRoles[i].selectedIndex].innerText,
            },)
        }

        $.ajax({
            type: 'PATCH',
            url: '/user/edit',
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(dataList)
        }).done(function () {
            alert('유저 역할이 수정되었습니다.');
            window.location.href = '/admin';
        }).fail(function (error) {
            console.log(dataList);
            alert(JSON.stringify(error));
        });
    },
}

post.init();
category.init();
users.init();