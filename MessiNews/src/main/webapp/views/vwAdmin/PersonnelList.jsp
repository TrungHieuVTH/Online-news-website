<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="subs" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Users>"/>
<jsp:useBean id="writers" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Users>"/>
<jsp:useBean id="editors" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Users>"/>
<jsp:useBean id="admins" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Users>"/>
<jsp:useBean id="check" scope="request" type="java.lang.String"/>

<m:main>
    <jsp:attribute name="js">
        <script>
            function confirmDelete(event) {
                if (!confirm("Bạn có chắc muốn xóa?")) {
                    event.preventDefault();
                }
            }

            function ChooseList(temp) {
                document.getElementById('divSub').style.display = 'none';
                document.getElementById('divWriter').style.display = 'none';
                // document.getElementById('divEditor').style.display = 'none';
                document.getElementById('divAdmin').style.display = 'none';

                switch (temp) {
                    case '1':
                        document.getElementById('divSub').style.display = 'block';
                        break;
                    case '2':
                        document.getElementById('divWriter').style.display = 'block';
                        break;
                    default:
                        document.getElementById('divAdmin').style.display = 'block';
                }
            }

            window.onload = ChooseList('${check}')

        </script>
    </jsp:attribute>
    <jsp:body>
        <form action="" method="post">
            <div class="d-flex justify-content-center bgColorGray">
                <!--    left-->
                <div class="bgColorGray" style="width: 15%">

                </div>

                <!--    center-->
                <div style="width: 70%; background-color: white" class="m-4 p-3">
                    <div class="d-flex justify-content-between">
                        <h4>Quản lý nhân sự</h4>

                        <div class="d-flex justify-content-between">
                            <a class="mx-1 px-3 btn btn-outline-success" onclick="ChooseList('1')"
                               style="border-radius: 20px" type="button">
                                <i class="fa fa-user" aria-hidden="true"></i>
                                Độc giả
                            </a>
                            <a class="mx-1 px-3 btn btn-outline-warning" onclick="ChooseList('2')"
                               style="border-radius: 20px" type="button">
                                <i class="fa fa-pencil-square" aria-hidden="true"></i>
                                Phóng viên
                            </a>
                            <a class="mx-1 px-3 btn btn-outline-danger" onclick="ChooseList('4')"
                               style="border-radius: 20px" type="button">
                                <i class="fa fa-male" aria-hidden="true"></i>
                                Admin
                            </a>
                        </div>
                    </div>
                    <hr>

                        <%--                    Danh sách độc giả--%>
                    <div id="divSub" style="display: block">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách độc giả</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Users/Add"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm tài khoản mới
                                </a>
                            </div>
                        </div>

                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td align="center">Tài khoản</td>
                                    <td align="center">Ngày tạo</td>
                                    <td align="center">Email</td>
                                    <td align="center">Hạn dùng (Ngày)</td>
                                    <td align="center"></td>
                                    <td align="center"></td>
                                </tr>
                                <c:forEach items="${subs}" var="c">
                                    <tr>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Users/Profile?id=${c.id}">
                                                    ${c.username}
                                            </a>
                                        </td>
                                        <td align="center">
                                            <script>
                                                document.write('${c.issue_at}'.slice(8, 10) + '/' + '${c.issue_at}'.slice(5, 7) + '/' + '${c.issue_at}'.slice(0, 4))
                                            </script>
                                        </td>
                                        <td align="center">${c.email}</td>
                                        <td align="center">
                                                ${c.expirationDate()<0 ? "Hết hạn": c.expirationDate()}
                                        </td>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Users/ExtendExp?id=${c.id}"
                                               role="button" class="btn btn-info">Gia hạn</a>
                                        </td>
                                        <td align="center">
                                            <button type="submit" onclick="confirmDelete(event)"
                                                    formaction="${pageContext.request.contextPath}/Admin/Users/Delete?id=${c.id}"
                                                    class="btn btn-danger">Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>


                        <%--                    Danh sách phóng viên--%>
                    <div id="divWriter" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách phóng viên</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Users/Add"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm tài khoản mới
                                </a>
                            </div>
                        </div>

                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td align="center">Tài khoản</td>
                                    <td align="center">Ngày tạo</td>
                                    <td align="center">Bút danh</td>
                                    <td align="center">Bài đã đăng</td>
                                    <td align="center">Tổng lượt xem</td>
                                    <td align="center">Bài bị từ chối</td>
                                    <td align="center">Bài cao cấp</td>
                                    <td align="center"></td>
                                </tr>
                                <c:forEach items="${writers}" var="c">
                                    <tr>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Users/Profile?id=${c.id}">
                                                    ${c.username}
                                            </a>
                                        </td>
                                        <td align="center">
                                            <script>
                                                document.write('${c.issue_at}'.slice(8, 10) + '/' + '${c.issue_at}'.slice(5, 7) + '/' + '${c.issue_at}'.slice(0, 4))
                                            </script>
                                        </td>
                                        <td align="center">${c.full_name}</td>
                                        <td align="center">${c.publishArticlesCount()}</td>
                                        <td align="center">${c.sumViews()}</td>
                                        <td align="center">${c.denyArticlesCount()}</td>
                                        <td align="center">${c.premiumCount()}</td>
                                        <td align="center">
                                            <button type="submit" onclick="confirmDelete(event)"
                                                    formaction="${pageContext.request.contextPath}/Admin/Users/Delete?id=${c.id}"
                                                    class="btn btn-danger">Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>


                        <%--                    Danh sách Admin--%>
                    <div id="divAdmin" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách Admin</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Users/Add"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm tài khoản mới
                                </a>
                            </div>
                        </div>

                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td align="center">Tài khoản</td>
                                    <td align="center">Ngày tạo</td>
                                    <td align="center">Họ tên</td>
                                    <td align="center">Ngày sinh</td>
                                    <td align="center">Email</td>
                                </tr>
                                <c:forEach items="${admins}" var="c">
                                    <tr>
                                        <td align="center">
                                                ${c.username}
                                        </td>
                                        <td align="center">
                                            <script>
                                                document.write('${c.issue_at}'.slice(8, 10) + '/' + '${c.issue_at}'.slice(5, 7) + '/' + '${c.issue_at}'.slice(0, 4))
                                            </script>
                                        </td>
                                        <td align="center">${c.full_name}</td>
                                        <td align="center">
                                            <script>
                                                document.write('${c.dob}'.slice(8, 10) + '/' + '${c.dob}'.slice(5, 7) + '/' + '${c.dob}'.slice(0, 4))
                                            </script>
                                        </td>

                                        <td align="center">${c.email}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>


                <!--    right-->
                <div style="width: 15%" class="d-flex align-items-end flex-column bgColorGray">
                    <div class="mt-auto p-2" style="position: fixed; bottom: 10px; right: 10px">
                        <a href="">
                            <i class="fa fa-arrow-circle-o-up fa-3x" aria-hidden="true"></i>
                        </a>
                    </div>
                </div>
            </div>
        </form>
    </jsp:body>
</m:main>