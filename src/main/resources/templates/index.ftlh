<#-- @ftlvariable name="membersList" type="java.util.List<gym.model.Membership>" -->
<#-- @ftlvariable name="curMembershipId" type="java.lang.Long" -->
<#-- @ftlvariable name="currentException" type="java.lang.Exception" -->

<#import "/spring.ftl" as spring/>

<html lang="ru">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>

<#if currentException?has_content>
    <h3>Error: ${currentException.getMessage()}</h3>
</#if>

<#if curMembershipId != -1>
    <h3>Prolongation of membership with id ${curMembershipId}</h3>
    <form method="POST" action="/prolongate-membership">
        <input type="text" name="duration"/>
        <input type="submit" value="Prolongate membership">
    </form>
</#if>

<table class="table">
    <#list membersList as item>
        <tr>
            <th>
                <form method="POST" action="/select-membership">
                    <button class="button" type="submit"
                            value="${item.getId()}"
                            name="identifier">
                        Select membership
                    </button>
                </form>
            </th>
            <th>${item.getId()}:  </th>
            <th>${item.getValidUntil()}</th>
        </tr>
    </#list>
</table>

<h3>Create new membership id, (duration in days)</h3>
<form method="POST" action="/new-membership">
    <input type="text" name="identifier"/>
    <input type="text" name="valid for"/>
    <input type="submit" value="Create new membership">
</form>

</body>
</html>
