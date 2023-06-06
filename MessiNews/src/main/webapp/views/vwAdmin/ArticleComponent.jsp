<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="allPCategories" scope="request"
             type="java.util.List<com.messi.king.messinews.model.bean.ParentCategories>"/>
<jsp:useBean id="allCategories" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Categories>"/>
<jsp:useBean id="tags" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Tags>"/>
<jsp:useBean id="check" scope="request" type="java.lang.String"/>

<m:main>
  <jsp:attribute name="js">
        <script>
            function confirmDelete(event) {
                if (!confirm("Bạn có chắc muốn xóa?")) {
                    event.preventDefault();
                }
            }

            function changeList(temp) {
                document.getElementById('divPCategories').style.display = 'none';
                document.getElementById('divCategories').style.display = 'none';
                document.getElementById('divTags').style.display = 'none';

                switch (temp){
                    case '1':
                        document.getElementById('divPCategories').style.display = 'block';
                        break;
                    case '2':
                        document.getElementById('divCategories').style.display = 'block';
                        break;
                    default:
                        document.getElementById('divTags').style.display = 'block';
                }
            }

            window.onload = changeList('${check}')

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
                        <h4>Quản lý thông tin bài báo</h4>

                        <div class="d-flex justify-content-between">
                            <div>
                                <a class="mx-1 px-3 btn btn-outline-info" onclick="changeList('1')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-list" aria-hidden="true"></i>
                                    Chuyên mục lớn
                                </a>
                                <a class="mx-1 px-3 btn btn-outline-warning" onclick="changeList('2')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-list-ul" aria-hidden="true"></i>
                                    Chuyên mục nhỏ
                                </a>
                                <a class="mx-1 px-3 btn btn-outline-danger" onclick="changeList('3')"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-tags" aria-hidden="true"></i>
                                    &ensp; Nhãn &ensp;
                                </a>
                            </div>
                        </div>
                    </div>
                    <hr>

<%--                    Danh sách chuyên mục lớn--%>

                    <div id="divPCategories">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách chuyên mục lớn</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Component/AddPCate"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm chuyên mục lớn
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td>ID</td>
                                    <td align="center">Tên chuyên mục</td>
                                    <td align="center"></td>
                                </tr>
                                <c:forEach items="${allPCategories}" var="c">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Component/EditPCate?id=${c.id}">
                                                    ${c.name_parent_cate}
                                            </a>
                                        </td>
                                        <td align="center">
                                            <button type="submit" onclick="confirmDelete(event)"
                                                    formaction="${pageContext.request.contextPath}/Admin/Component/DeletePCate?idPCate=${c.id}"
                                                    class="btn btn-danger">Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

<%--                    Danh sách chuyên mục nhỏ--%>
                    <div id="divCategories" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách chuyên nhỏ</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Component/AddCate"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm chuyên mục nhỏ
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td>ID</td>
                                    <td align="center">Tên chuyên mục</td>
                                    <td align="center">Thuộc chuyên mục lớn</td>
                                    <td align="center">Xóa</td>
                                </tr>
                                <c:forEach items="${allCategories}" var="c">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Component/EditCate?id=${c.id}">
                                                    ${c.name_category}
                                            </a>
                                        </td>
                                        <td align="center">
                                            <c:forEach items="${allPCategories}" var="d">
                                                <c:if test="${c.parent_cate_id==d.id}">
                                                    ${d.name_parent_cate}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td align="center">
                                            <button type="submit" onclick="confirmDelete(event)"
                                                    formaction="${pageContext.request.contextPath}/Admin/Component/DeleteCate?idCate=${c.id}"
                                                    class="btn btn-danger">Xóa
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

<%--                    Danh sách nhãn--%>

                    <div id="divTags" style="display: none">
                        <div class="mb-3 d-flex justify-content-between">
                            <h5>Danh sách nhãn</h5>
                            <div>
                                <a href="${pageContext.request.contextPath}/Admin/Component/AddTag"
                                   class="btn btn-outline-success"
                                   style="border-radius: 20px" type="button">
                                    <i class="fa fa-plus" aria-hidden="true"></i>
                                    Thêm nhãn
                                </a>
                            </div>
                        </div>
                        <div>
                            <table class="w-100" cellpadding="5px">
                                <tr style="background-color: #EEEEEE">
                                    <td>ID</td>
                                    <td align="center">Tên tag</td>
                                    <td align="center"></td>
                                </tr>
                                <c:forEach items="${tags}" var="c">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td align="center">
                                            <a href="${pageContext.request.contextPath}/Admin/Component/EditTag?id=${c.id}">
                                                    ${c.name_tags}
                                            </a>
                                        </td>
                                        <td align="center">
                                            <button type="submit" onclick="confirmDelete(event)"
                                                    formaction="${pageContext.request.contextPath}/Admin/Component/DeleteTag?id=${c.id}"
                                                    class="btn btn-danger">Xóa
                                            </button>
                                        </td>
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
