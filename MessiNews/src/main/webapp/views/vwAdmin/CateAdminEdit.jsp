<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="allPCategories" scope="request"
             type="java.util.List<com.messi.king.messinews.model.bean.ParentCategories>"/>
<jsp:useBean id="cate" scope="request" type="com.messi.king.messinews.model.bean.Categories"/>

<m:main>
    <jsp:attribute name="css">
          <style>
              .btStyle {
                  width: 350px;
                  border-radius: 5px;
              }

          </style>
    </jsp:attribute>
    <jsp:attribute name="js">
        <script>
            function choosePCat(url, name) {
                document.getElementById('btPCat').innerText = name;
                document.getElementById('form').action = url;
            }

        </script>
    </jsp:attribute>
    <jsp:body>
        <form id="form" action="" method="post">
            <div class="d-flex justify-content-center bgColorGray">
                <!--    left-->
                <div class="bgColorGray" style="width: 15%">

                </div>

                <!--    center-->
                <div style="width: 70%; background-color: white" class="m-4 p-3">
                    <h4>Chỉnh sửa chuyên mục nhỏ</h4>

                    <hr>
                    <h5>Đang chỉnh chuyên mục nhỏ: ${cate.name_category}</h5>
                    <br>
                    <label for="nameCate">Tên chuyên mục nhỏ</label>
                    <input type="text" class="form-control" name="nameCate" id="nameCate" autofocus required
                           value="${cate.name_category}"/>

                    <c:forEach items="${allPCategories}" var="c">
                        <c:if test="${c.id == cate.parent_cate_id}">
                            <c:set var="namePCat" value="${c.name_parent_cate}"/>
                        </c:if>
                    </c:forEach>

                    <div class="dropdown mt-3">
                        <button class="btn btn-outline-secondary btStyle" type="button" id="btPCat"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${namePCat}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <c:forEach items="${allPCategories}" var="c">
                                <a class="dropdown-item"
                                   onclick="choosePCat('${pageContext.request.contextPath}/Admin/Component/EditCate?idCate=${cate.id}&idPCate=${c.id}','${c.name_parent_cate}')">${c.name_parent_cate}</a>
                            </c:forEach>
                        </div>
                    </div>
                    <hr>
                    <div id="save" align="end">
                        <a href="${pageContext.request.contextPath}/Admin/Component/List"
                           class="btn btn-secondary mr-2">
                            <i class="fa fa-times" aria-hidden="true"></i>
                            Hủy bỏ
                        </a>
                        <button class="btn btn-success" type="submit" formaction="${pageContext.request.contextPath}/Admin/Component/EditCate?idCate=${cate.id}&idPCate=${cate.parent_cate_id}">
                            <i class="fa fa-check" aria-hidden="true"></i>
                            Lưu lại
                        </button>
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
