<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="articles" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Articles>"/>
<jsp:useBean id="titleTopic" scope="request" type="java.lang.String"/>
<jsp:useBean id="cateRelated" scope="request" type="java.util.List<com.messi.king.messinews.model.bean.Categories>"/>

<jsp:useBean id="url" scope="request" type="java.lang.String"/>
<jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="maxPage" scope="request" type="java.lang.Integer"/>

<m:main>
    <jsp:attribute name="js">
          <script>
              function checkPermission(premium, event) {
                  if (premium == 1) {
                      if (!${auth}) {
                          alert("Bạn phải đăng nhập mới xem được bài này")
                          event.preventDefault();
                      } else {
                          if (${authUser.checkExpiration()==0}) {
                              alert("Bạn cần phải gia hạn tài khoản để có thể xem bài này")
                              event.preventDefault();
                          }
                      }
                  }
              }
          </script>
      </jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-center">
            <div style="width: 15%">
            </div>
            <div style="width: 70%">

                    <%--            Tiêu đề và danh mục--%>
                <div align="center">
                    <h1><i><b>${titleTopic}</b></i></h1>
                    <div class="d-flex justify-content-center w-100 my-4">
                        <c:forEach items="${cateRelated}" var="c">
                            <a class="mx-3"
                               href="${pageContext.request.contextPath}/Home/ByCat?id=${c.id}"><b>${c.name_category}</b></a>
                        </c:forEach>
                    </div>
                    <hr>
                </div>
                <c:choose>
                    <c:when test="${articles.size() == 0}">
                        <div class="d-flex justify-content-center bgColorGray p-5">
                            <b>Chưa có bài báo nào thuộc chuyên mục này</b>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%--            các bài báo--%>
                        <div>
                                <%--                Bài lớn nhất--%>
                            <a href="${pageContext.request.contextPath}/Home/Details?id=${articles[0].id}" onclick="checkPermission(${articles[0].premium},event)">
                                <div class="d-flex justify-content-between bgColorGray">

                                    <div style="width: 60%">
                                        <img src="${pageContext.request.contextPath}/photos/articles/${articles[0].id}/a.png"
                                             alt=""
                                             class="w-100">
                                    </div>
                                    <div class="p-3 d-flex justify-content-between flex-column" style="width: 40%">
                                        <div>
                                            <h3 class="mb-0">
                                                <c:if test="${articles[0].premium == 1}">
                                                    <i class="fa fa-star" aria-hidden="true" style="color: gold"></i>
                                                </c:if>
                                                    ${articles[0].title}
                                            </h3>
                                            <p>
                                                <script>
                                                    document.write('${articles[0].publish_date}'.slice(8, 10) + '/' + '${articles[0].publish_date}'.slice(5, 7) + '/' + '${articles[0].publish_date}'.slice(0, 4))
                                                </script>
                                            </p>
                                            <p>${articles[0].abstract_content}</p>
                                        </div>
                                        <div>
                                            <img class="imageIcon"
                                                 src="${pageContext.request.contextPath}/photos/userAvatar/${articles[0].writer_id}/avatar.png"
                                                 alt="">
                                                ${articles[0].getWriterName(articles[0].writer_id)}
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <hr>
                            <br>

                                <%--                các bài nhỏ--%>
                            <c:forEach items="${articles}" begin="1" var="c">
                                <a href="${pageContext.request.contextPath}/Home/Details?id=${c.id}" onclick="checkPermission(${c.premium},event)">
                                    <div class="d-flex justify-content-between bgColorGray">
                                        <div style="width: 30%">
                                            <img src="${pageContext.request.contextPath}/photos/articles/${c.id}/a.png"
                                                 alt=""
                                                 class="w-100">
                                        </div>
                                        <div class="p-3 d-flex justify-content-between flex-column" style="width: 70%">
                                            <div>
                                                <h4 class="mb-0">
                                                    <c:if test="${c.premium == 1}">
                                                        <i class="fa fa-star" aria-hidden="true"
                                                           style="color: gold"></i>
                                                    </c:if>
                                                        ${c.title}
                                                </h4>
                                                <p>
                                                    <script>
                                                        document.write('${c.publish_date}'.slice(8, 10) + '/' + '${c.publish_date}'.slice(5, 7) + '/' + '${c.publish_date}'.slice(0, 4))
                                                    </script>
                                                </p>
                                                <p>${c.abstract_content}</p>
                                            </div>

                                            <div>
                                                <img class="imageIcon"
                                                     src="${pageContext.request.contextPath}/photos/userAvatar/${c.writer_id}/avatar.png"
                                                     alt="">
                                                    ${c.getWriterName(c.writer_id)}
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <hr>
                                <br>
                            </c:forEach>
                        </div>

<%--                        Phân trang--%>
                        <div class="mt-5 mb-3 w-100 d-flex justify-content-center">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination">
                                    <li class="page-item">
                                        <a class="page-link" href="${pageContext.request.contextPath}/Home${url}&page=1">
                                            <i class="fa fa-fast-backward" aria-hidden="true"></i>
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <c:if test="${currentPage==1}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/Home${url}&page=1">
                                                <i class="fa fa-step-backward" aria-hidden="true"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${currentPage>1}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Home${url}&page=${currentPage-1}">
                                                <i class="fa fa-step-backward" aria-hidden="true"></i>
                                            </a>
                                        </c:if>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageContext.request.contextPath}/Home${url}&page=${currentPage}">
                                                ${currentPage}
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <c:if test="${currentPage==maxPage}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Home${url}&page=${maxPage}">
                                                <i class="fa fa-step-forward" aria-hidden="true"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${currentPage<maxPage}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Home${url}&page=${currentPage+1}">
                                                <i class="fa fa-step-forward" aria-hidden="true"></i>
                                            </a>
                                        </c:if>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/Home${url}&page=${maxPage}">
                                            <i class="fa fa-fast-forward" aria-hidden="true"></i>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>

                    </c:otherwise>
                </c:choose>
            </div>
                <%--            left--%>
            <div style="width: 15%" class="d-flex align-items-end flex-column">
                <div class="mt-auto p-2" style="position: fixed; bottom: 10px; right: 10px">
                    <a href="">
                        <i class="fa fa-arrow-circle-o-up fa-3x" aria-hidden="true"></i>
                    </a>
                </div>
            </div>
        </div>
    </jsp:body>
</m:main>
