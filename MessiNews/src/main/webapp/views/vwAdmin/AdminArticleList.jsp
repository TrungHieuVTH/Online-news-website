<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="articlesList" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Articles>"/>
<jsp:useBean id="allPCategories" scope="request"
             type="java.util.List<com.messi.king.messinews.model.bean.ParentCategories>"/>
<jsp:useBean id="allCategories" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Categories>"/>

<m:main>
     <jsp:attribute name="css">
          <link rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.min.css"
                integrity="sha512-f0tzWhCwVFS3WeYaofoLWkTP62ObhewQ1EZn65oSYDZUg1+CyywGKkWzm8BxaJj5HGKI72PnMH9jYyIFz+GH7g=="
                crossorigin="anonymous" referrerpolicy="no-referrer"/>
    </jsp:attribute>
    <jsp:attribute name="js">
        <script>
            function ChooseList(temp) {
                document.getElementById('divAccept').style.display = 'none';
                document.getElementById('divDraft').style.display = 'none';
                document.getElementById('divDeny').style.display = 'none';

                switch (temp) {
                    case '1':
                        document.getElementById('divAccept').style.display = 'block';
                        break;
                    case '2':
                        document.getElementById('divDraft').style.display = 'block';
                        break;
                    default:
                        document.getElementById('divDeny').style.display = 'block';
                }
            }
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
                        <h4>Quản lý bài báo</h4>

                        <div class="d-flex justify-content-between">
                            <div>
                                <a class="mx-1 px-3 btn btn-outline-success" onclick="ChooseList('1')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-check" aria-hidden="true"></i>
                                    Đã duyệt
                                </a>
                                <a class="mx-1 px-3 btn btn-outline-warning" onclick="ChooseList('2')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-file-text-o" aria-hidden="true"></i>
                                    Bản thảo
                                </a>
                                <a class="mx-1 px-3 btn btn-outline-danger" onclick="ChooseList('3')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-times" aria-hidden="true"></i>
                                    Bị từ chối
                                </a>
                            </div>
                        </div>
                    </div>
                    <hr>

                        <%--                    Danh sách các bài đã duyệt--%>
                    <div id="divAccept">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách các bài đã duyệt</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Articles/Upload"
                                   class="mx-1 px-3 btn btn-outline-info"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm bài viết mới
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td style="width: 35%">Tiêu đề</td>
                                    <td align="center" style="width: 15%">Chuyên mục</td>
                                    <td align="center" style="width: 20%">Tác giả</td>
                                    <td align="center" style="width: 10%">Ngày đăng</td>
                                    <td align="center" style="width: 10%">Lượt xem</td>
                                    <td align="center" style="width: 10%">Loại báo</td>

                                </tr>
                                <c:forEach items="${articlesList}" var="c">
                                    <c:if test="${c.status==1}">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/Home/Details?id=${c.id}">
                                                        ${c.title}
                                                </a>
                                            </td>
                                            <td align="center">
                                                    ${c.getCategoriesName(c.categories_id)}
                                            </td>
                                            <td align="center">
                                                    ${c.getWriterName(c.writer_id)}
                                            </td>
                                            <td align="center">
                                                <script>
                                                    document.write('${c.publish_date}'.slice(8, 10) + '/' + '${c.publish_date}'.slice(5, 7) + '/' + '${c.publish_date}'.slice(0, 4))
                                                </script>
                                            </td>
                                            <td align="center">
                                                    ${c.views}
                                            </td>
                                            <td align="center">
                                                <c:if test="${c.premium == 0}">
                                                    <i class="fa fa-star" aria-hidden="true" style="color: grey"></i>
                                                </c:if>
                                                <c:if test="${c.premium == 1}">
                                                    <i class="fa fa-star" aria-hidden="true" style="color: gold"></i>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

                        <%--                    Danh sách các bản thảo--%>
                    <div id="divDraft" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách các bản thảo</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Articles/Upload"
                                   class="mx-1 px-3 btn btn-outline-info"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm bài viết mới
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td>Tiêu đề</td>
                                    <td align="center">Tác giả</td>
                                    <td align="center"></td>
                                    <td align="center"></td>
                                </tr>
                                <c:forEach items="${articlesList}" var="c">
                                    <c:if test="${c.status==-1}">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/Admin/Articles/ViewArticle?id=${c.id}">
                                                        ${c.title}
                                                </a>
                                            </td>
                                            <td align="center">
                                                    ${c.getWriterName(c.writer_id)}
                                            </td>
                                            <td align="center">
                                                <a type="button"
                                                   href="${pageContext.request.contextPath}/Admin/Articles/Accept?id=${c.id}"
                                                   class="btn btn-success">Duyệt
                                                </a>
                                            </td>
                                            <td align="center">
                                                <a type="button"
                                                   href="${pageContext.request.contextPath}/Admin/Articles//Deny?id=${c.id}"
                                                   class="btn btn-danger">Từ chối
                                                </a>
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

                        <%--                    Danh sách các bài bị từ chối--%>
                    <div id="divDeny" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách các bài bị từ chối</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Articles/Upload"
                                   class="mx-1 px-3 btn btn-outline-info"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm bài viết mới
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td style="width: 35%">Tiêu đề</td>
                                    <td align="center" style="width: 15%">Chuyên mục</td>
                                    <td align="center" style="width: 20%">Tác giả</td>
                                    <td align="center" style="width: 30%">Lý do</td>
                                </tr>
                                <c:forEach items="${articlesList}" var="c">
                                    <c:if test="${c.status==0}">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/Admin/Articles/ViewArticle?id=${c.id}">
                                                        ${c.title}
                                                </a>
                                            </td>
                                            <td align="center">
                                                    ${c.getCategoriesName(c.categories_id)}
                                            </td>
                                            <td align="center">
                                                    ${c.getWriterName(c.writer_id)}
                                            </td>
                                            <td align="center">
                                                    ${c.reason}
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>

                <!--    right-->
                <div style="width: 15%" class="d-flex align-items-end flex-column bgColorGray">
                </div>
            </div>
        </form>
    </jsp:body>
</m:main>
