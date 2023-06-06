<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" scope="request" type="com.messi.king.messinews.model.bean.Users"/>

<m:main>
    <jsp:attribute name="css">
          <link href="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/css/fileinput.min.css" media="all"
                rel="stylesheet" type="text/css"/>
          <link rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.min.css"
                integrity="sha512-f0tzWhCwVFS3WeYaofoLWkTP62ObhewQ1EZn65oSYDZUg1+CyywGKkWzm8BxaJj5HGKI72PnMH9jYyIFz+GH7g=="
                crossorigin="anonymous" referrerpolicy="no-referrer"/>
          <style>

              .inputOff {
                  border-style: none;
                  height: 40px;
                  border-radius: 10px;
              }

              .inputOff:focus {
                  border-style: none;
                  outline: none;
              }

              .inputOn {
                  border-style: solid;
                  height: 40px;
                  border-radius: 10px;
                  border-color: darkgrey;
              }

              .inputOn:focus {
                  outline: darkgrey;
              }
          </style>
      </jsp:attribute>
    <jsp:attribute name="js">
        <script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.2.5/js/fileinput.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.2.5/themes/fa/theme.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.2.5/js/locales/vi.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.min.js"
                integrity="sha512-AIOTidJAcHBH2G/oZv9viEGXRqDNmfdPVPYOYKGy3fti0xIplnlgMHUGfuNRzC6FkzIo0iIxgFnr9RikFxK+sw=="
                crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script>

            function editClick() {
                document.getElementById("newFullName").classList.add('inputOn');
                document.getElementById("newFullName").classList.remove('inputOff');
                document.getElementById('newFullName').readOnly = false;

                document.getElementById("newEmail").classList.add('inputOn');
                document.getElementById("newEmail").classList.remove('inputOff');
                document.getElementById('newEmail').readOnly = false;

                document.getElementById("newDob").classList.add('inputOn');
                document.getElementById("newDob").classList.remove('inputOff');
                document.getElementById('newDob').readOnly = false;

                document.getElementById('btRole').disabled = false;
                document.getElementById("btRole").classList.add('inputOn');
                document.getElementById("btRole").classList.remove('inputOff');

                document.getElementById('save').style.display = 'block';
                document.getElementById('divEditProfile').style.display = 'block';
                document.getElementById('divResetPass').style.display = 'none';
                document.getElementById('title').innerText = 'Chỉnh sửa thông tin';

            }

            function chooseRole(idRole, name) {
                document.getElementById('btRole').innerText = name;
                document.getElementById('role').value = idRole
            }

            function resetPassClick(){
                document.getElementById('divEditProfile').style.display = 'none';
                document.getElementById('divResetPass').style.display = 'block';
                document.getElementById('title').innerText = 'Đặt lại mật khẩu';
            }

            $('#resetPassForm').on('submit', function (e) {
                e.preventDefault()

                if (document.getElementById('newPassword').value != document.getElementById('confirmNewPassword').value){
                    alert('Xác nhận mật khẩu chưa chính xác')
                    return
                }
                $('#resetPassForm').off('submit').submit();
            })

            window.onload = function () {
                let str = '${user.dob}';
                $('#newDob').datetimepicker({
                    value: new Date((str.split('T')[0].replaceAll('-', '/'))),
                    format: 'd/m/Y',
                    timepicker: false,
                    mask: true
                });
            }
        </script>
  </jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-center bgColorGray">
            <!--    left-->
            <div class="bgColorGray" style="width: 15%">

            </div>

            <!--    center-->
            <div style="width: 70%; background-color: white" class="my-4 p-3">
                <div class="d-flex justify-content-between">
                    <h3>
                        <a href="${pageContext.request.contextPath}/Admin/Users/List" class="mr-1">
                            <i class="fa fa-caret-left" aria-hidden="true"></i>
                        </a>
                        <span id="title">
                            Thông tin cá nhân
                        </span>
                    </h3>
                    <div class="d-flex justify-content-between">
                        <button onclick="editClick()" class="mx-1 px-3 btn btn-outline-success"
                                style="border-radius: 20px" type="button">
                            <i class="fa fa-pencil" aria-hidden="true"></i>
                            Chỉnh sửa thông tin
                        </button>

                        <button onclick="resetPassClick()" class="mx-1 px-3 btn btn-outline-warning"
                                style="border-radius: 20px" type="button">
                            <i class="fa fa-key" aria-hidden="true"></i>
                            Đặt lại mật khẩu
                        </button>
                    </div>
                </div>
                <hr>

                <div id="divEditProfile">
                    <form action="${pageContext.request.contextPath}/Admin/Users/EditProfile?id=${user.id}" method="post">
                        <table cellpadding="5px" class="w-100">
                            <tr>
                                <td>
                                    Tài khoản:
                                </td>
                                <td>
                                    <input type="text" class="pl-2 inputOff" value="${user.username}" readonly="readonly" >
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 10%">Họ vàn tên:</td>
                                <td>
                                    <input id="newFullName" name="newFullName" type="text"
                                           value="${user.full_name}"
                                           class="w-100 pl-2 inputOff" readonly="readonly" required>
                                </td>
                            </tr>
                            <tr>
                                <td>Ngày sinh:</td>
                                <td>
                                    <input id="newDob" name="newDob" type="text" class="pl-2 inputOff w-100" required>
                                </td>
                            </tr>
                            <tr>
                                <td>Email:</td>
                                <td>
                                    <input id="newEmail" name="newEmail" type="email" value="${user.email}"
                                           class="w-100 pl-2 inputOff" readonly="readonly" required>
                                </td>
                            </tr>
                            <tr>
                                <td>Vai trò:</td>
                                <td>
                                    <div class="dropdown">
                                        <button id="btRole"
                                                class="btn inputOff dropdown-toggle w-100 d-flex justify-content-between align-items-center "
                                                type="button" data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false"
                                                disabled>
                                            <c:choose>
                                                <c:when test="${user.role == 1}">
                                                    Độc giả
                                                </c:when>

                                                <c:when test="${user.role == 2}">
                                                    Phóng viên
                                                </c:when>

                                                <c:when test="${user.role == 3}">
                                                    Nhà báo
                                                </c:when>

                                                <c:when test="${user.role == 4}">
                                                    Quản trị viên
                                                </c:when>
                                            </c:choose>
                                        </button>
                                        <input id="role" name="role" type="text" value="${user.role}" style="display: none">
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item"
                                               onclick="chooseRole('1','Độc giả')">
                                                Độc giả</a>
                                            <a class="dropdown-item"
                                               onclick="chooseRole('2','Phóng viên')">Phóng
                                                viên</a>
                                            <a class="dropdown-item"
                                               onclick="chooseRole('3','Biên tập')">Biên
                                                tập</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <br>
                        <div id="save" align="end" style="display: none">
                            <a href="${pageContext.request.contextPath}/Admin/Users/Profile?id=${user.id}"
                               class="btn btn-secondary mr-2">
                                <i class="fa fa-times" aria-hidden="true"></i>
                                Hủy bỏ
                            </a>
                            <button class="btn btn-success" type="submit">
                                <i class="fa fa-check" aria-hidden="true"></i>
                                Lưu lại
                            </button>
                        </div>
                    </form>
                </div>


                <div id="divResetPass" style="display: none">
                    <form id="resetPassForm" action="${pageContext.request.contextPath}/Admin/Users/ResetPass?id=${user.id}" method="post">
                        <table cellpadding="5px" class="w-100">
                            <tr>
                                <td width="103px">
                                    Tài khoản:
                                </td>
                                <td>
                                    <input type="text" class="pl-2 inputOff" value="${user.username}" readonly="readonly" >
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input id="newPassword" name="rawpwd" type="password" placeholder="Mật khẩu mới"
                                           class="w-100 pl-2 inputOn">

                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input id="confirmNewPassword" type="password"
                                           placeholder="Xác nhận mật khẩu mới"
                                           class="w-100 pl-2 inputOn">
                                </td>
                            </tr>
                        </table>
                        <br>
                        <div align="end">
                            <a href="${pageContext.request.contextPath}/Admin/Users/Profile?id=${user.id}"
                               class="btn btn-secondary mr-2">
                                <i class="fa fa-times" aria-hidden="true"></i>
                                Hủy bỏ
                            </a>
                            <button class="btn btn-success" type="submit">
                                <i class="fa fa-check" aria-hidden="true"></i>
                                Lưu lại
                            </button>
                        </div>
                    </form>
                </div>

            </div>
            <!--    right-->
            <div style="width: 15%" class="d-flex align-items-end flex-column bgColorGray">
            </div>
        </div>
    </jsp:body>
</m:main>
