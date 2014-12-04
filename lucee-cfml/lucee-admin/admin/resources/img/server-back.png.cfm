<!--- 
 *
 * Copyright (c) 2014, the Railo Company LLC. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ---><cfsavecontent variable='content'>iVBORw0KGgoAAAANSUhEUgAAAD4AAAC+CAIAAADiEyvLAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAGHdJREFUeNrsXcuS7DqOExT+1vmz+cfeoSstPgDaeXs2s+iIvNF9oioftixRIgiCLPzP+m/9b6/f0H9D/w39N/Tf0H9D/w39N/Tf0H9D/w39N/Tf0H9D/w39N/Tf0H9D/w39N/Tf0H9D/w39N/Tf0H9D/w39N/T/n6Hjy0vIH/GfvoEvN/t+ha8vvl4R96/XeZ2Pz/y9zXqV/S7tNuDnBcI+ZZ+k3/fbz/RvrXN3xD3s0vensXDVTOgTIN7+MnP9Sb4MJd/F1zX8jEuuA70OenAyBp3dmFXumkKwpxMY99aX7yvC5l5HCb4tsVgFeE9av5iP4caDcdl+l2nrsDeI+wv8jI0yWMh3znyc+Tqvx83dkpBLFGag5nxf6X6ERTNXwA3/OR1lGdcwo78pwRnjPR7wsdjHvLE2ezOAOE8zd9uZgIX991k+BtRLGt9l7B15PnlaHQzKYD6PW4/M2KGfZ8A99yve7GHlIqflUEbRIwLPsn2mAYjtpQbJ/u79ifPwcSsAPeVl9Ih/7hPmvgY/owEx9wrbuo9plKGcB4iZx23Bx3buL50bsg1s9eiPQfYux9j098rHd9mrer54RovPSqLtMp+Psfd0GzLsA2Vy8WDorRZrdS85z2usx4wf+6qEHensi9VtGQvw+cm++/n8ZQdCLR1zDyGOVcDPVfb5sOTwRp10aTo85wnrrI5ByYKE4TDcRB2Usp6feWeuQOzuXYPOT8awdjlb9LDDoPi3WG58OHuxN1w8OtiHxn16wf0D5Cqf54nPxw4rz/TZlHVWQVwSzUcgbbOenOx9GP6zvh+/xceOaSHdBsU6VltULVVY1NkenIcTY6Ypi462tr+H+Zv5uAdrL6NOhTMJn5PomCpjQfIJoSAn7DF29j5jvG+e7gOxRKhdkVsKsiURp/C5p5zRMVFnUFdaIPPYivOqji7oQiGcVm0GWZ5zIcbSx4yWtdSs3+/x/LtyJcJfIDcr0uOdY4+o0+J2EMCZTeA4VZQHOe99/EhuwsSZrBfDFaw2Ht7bAztfwvFtPANKU0E4ZohDqi0ODPx0WwUUpKT74Z+tH0sRC4edF5CJkW1/TxrFzQduYGydVa6HCSjYzjWOEcOWsWTg8Pu3yQF5mvdWuxJfMNaljPg29TiXAAGVCUny4MzrlUMSV6a+FQYn2hWeh5S9bzhCnSOWnPwfDBPzH7evp0aBMQzYztoVtNOQYkFt2o7D82vizbDyMOLqTdcuqoBT3DCd6lWnzb4P+3OtzdovHP7q2N9tFGHQeT7iLa5ihSyJ6sJU5Xz9GMRe7ttir96uQC7fRop1oVe7Vq52YB4DsS6JZM5R0BAVMlk5KMoBgfB4BW5yYrHk6AAojuW+N8R2c13rBNptegmPJEKBXBfLXCGWfliC3PgV9UriQTtra7Mzz/j6Z6U7h8Qc4RxQgchn1hsEnhHmLjwHAyxwdByTz0KLqsr7SpxzZly9a25OrDzhA3viXG6Ce+RFegJ3n6kdUGHEVzkyKuiDx+f6jNtPlPKX+y2yBsLX1iYXK1yweI+6pa7VB8NXOmEL8K6wDz35ChtnHCQBJcbrTyYC84Jz38Ns0s+/l+9w0gHQ6BFjiCM0puyMFwIDT3IGEgT3+UY8GJtrPaYBb/QNHqTKmDlYMNrn6RYHuP5xyuUuZc/YYpX6lV0U0nqje/zKensbzQhCmu+I4IB2ibcletJMX0ZiL+4tS7AtwLK1hVvqlggXFnWoM40rb5nCLZsb5tjH3u3Njwetd369BPoV3AtwR5vJfmulW+6Qk72OrEh8UhpcjfUCeOyO+fwbDxbA0c0NvySibfjMwnl5GJ2zeoRQRaFY7Og2xmJpAugsGD8o3FVDjfo/7XbC1JTPakalkC5yPGAtPTYFRykI/Au3zKzlO7VPDpeEdHf31U4EBMpRSzt24hYdSp3g68SmNFbXYHSNBk25uM1JYF8+EDlV7ChY6cJEeWzOb2xkW5LmjJuowgkgQYPHWGMj1cEfoWrRYOFzlRIK9By+2aJ9izLh1FHETUTTaYlpsHzdat/x403X7riIi5DHPHZ1iF1KuKi8CW7H9gEouzFuI/LaKqtDkmTSZDdVwNwR1zKq7Hxg53DuAG+NYLugV4XTkGMi4oSInybvDth5EaRVoFdakBXjTBiOfLLcY3I25YmUY4oNdwUHEjctBB3PFq+Xld/Tz1WUBJQcZYYOeWBxNRlENgSEJiwAKtFDyR5guvcwgBNHbAWDW07SfaDoOePxQZgZoR4G4cPefMAgqAzKjqkOMky4yAP44wTMQGkptC5eMWLzdvLsjcfCsDcPg+Hqi8cHPZyZ84A1YOQyA0hTR8f2io5h8WeZg8K9sqwGZOUEPy5JIjIawZtkaRhJepYGMhKQgYZXhYgyirqHaqRe3GI1AldUyaQW0aHp39Wa99yxnoSRJGj2rG2//N8qCLTjqY+l08OPiNCaCr8j9zhv82D9s8CdGz1jaXGReUZvBgF1tfUrQ5LHUMVmEKfrlNJjY48AGxZtoBC5sL5/Fzw8f9A+Esj1MSqZrsDrmuwyCAqlYzpfQKNZYutC+K+Z12sMbFdvI59sG4oMSFZOeEvZG1dZI5/xTgKlvcxMlWyUtCc8GhZKQ9w8+pqFzqDxSi0I5cSzlG2O5NpL04i9yHYDj1MnmHuEOeNOI5KCx9R8z7G+RCQveB0TWH//JmZKUBzk4yJvVxuffSSWuf5TcFQfvJrhYCcqLMOPTkBojjGxAps1HzEezWXKK/M5iGeCtg5n4YjLDdw/XJtFDce7O4/vJP814RFPwAyIXm4ZT/85hDOR1pT8cc9cwh0bIT1D+0F5Kj12lYvqbEzkFQn1ePGwfRov9VDFTi/Ld+ZukfVrDjs8zJ0RKgQlR9MdIVSa+3z6+Idzh0uo/96wsvSQwE+SfbIIiZP4JPYkTYnOAZaBZk4/T0Ql6GjkGoW8DNjKq7BwobicgaYfNQtU01wzuCPirEzxiiQDudSFqX8FLRFAOrXa7HGg+oqci1bCB8OAhWSbz5bwNoKJXqvMfzRFvCtPms+IjvTUJye0YZucHA2rkiuJhHWhVmeQhXOsSGGxzwXZ9pRsXk4DBDBRk+6ZmNur9mRQuZvNfiA8f02VAjY2Z955JZHNsEONTm0B6GhsyFUo+fyGGRCt0PBUlbTRdPiZIbhHgykhiA5Cqa4u0OG53gU8YMaZWWIp7DoWSookBqWhgCNUYg10ZQh/qcso1Uf7sb7SWr6kVHokKaQwMWpOkyepJPi1M0fGIEJy7Q2gFqiIKn2KOKgIc5HcmPPg/cWYnFzjQIPX+U5izpNdRcL7/rbFtT19J2uZz8vgenqXIlMZGcg1Hq280CPT2KGazMBqJu2A8E7I3HoLUTG02UHznjLVod1pYspvnXbgoLPjILh8D3jhi+EpGHPBTWZMzdvSDI7mL2hpmsesTE8yoeFzC2Age3Nh+aQUxB4/K3J8IbVBDAZNo01ybYe5nnt/CjWpysXlx69BSBcHDagaUdJ2cdtITgg0ecL+DznffhAaJwbQHxy5qnF0FHxkTSzXt961rdcrIG4RAobPbmYVDmJHykkZj1RLvWWmXig0f7aHBLkucKEz4SP4oeumaKHx4nqXND5+5thfAqnHhmKz9UIRY0iKO/mIGZg8SJypNKaoYztUN5VVAeVwXqSARPE2Ic2BQUXBPf8QOeGDYbAs7CUlPBu+Lxm4QvTJB4fYKX+Lr7NgbGlSnfAi5D7tt9rxwzLjDRFukjrDcXa2oSBTQ0RVV7JPayoPQNlGWMrAah5Bo0mOnScXMa2UEBSivL5AiSWDaCZ7S1EmiC3g6xQKD3GlkpyATcG4m8z0c56GJmXygBak0w5GMKQw+yDn++EuzFRI8EK0H19Ol3hIlI/nYfkEDiUk6DClIDhbo6Nak6lip5LWDddqm1JC4QMaz9wsrAY7cdjGlKT1YrPJdC4hTplis8Z1jJ0guYZwlpDHk6REcqjow7pFZbwyxiFKOslGY2i5h+UJ9ypVGDUaFnkUjYdqqZCAKnGVbFXMPQuCtsVtIcnQz9sXUpwEiRrR2mlVnlFKBtrJQpIplRoq+ZMoGyJRtWoRjgTgfpLdi1Iq8Ei25JIwd2J8+FLxyRsl+yZ0GEUbaAqu59FrVKborvVGfDCYFROkKbE4ch4FD1O2hvVC2dcZo0erID4ICcznIbzewKT8zt7qFVupqEnAIqG0skCyy7QzVFk2rGxJ0PrutAcrZ2BwiyYW5EMgMVjWJrsTnmv013IpB+DXfugv5qDl7BQ8GLtUBgxdOUlrPVGrIaFX1cUAcEvxdrIxl4b++wvOfJPyvAhC9rAPONf+BgD3+iqLgwuDZh4SHWqgsPf2zD4EwW0v2/qHUrXx2HwEYfAaqdfLKVoelDczSoJnHEzC2/y0x34Y/DL0I3y9mdE1Ej51WpK+tQt6jviKmdVQmepkAEV4keCh4fvtTQ3kCOAaONZ0sA4+wl2VJmgLj16wqgtHciS7i1E89K+sNDrte3J/kUG939X8EiEsVYj/XWT19/ldOc1SusTzft6F64/CkrOSInE1j7b7Qs1EaWa1uiCZ1KPpkbKJCfMbf3d2FmDnM/ZbGeGyeg723qfQyLks9fzHeK7m67UiqWlHtRuWIoldwJh8NCCEOk2tMBSxKdfIUT2LPYWT5xKpbKLS4BzphTGZlueXmtnOyrovG4xSX4py0nRSYLX2IRMLh8WGqzOMsqVxBVen4C0Xl3EAmTx0fDGLvKgand1lKKiCoBHAoo8PREiKjn2gwEy9bCyZ4pqAadeOdUPlIWqH7CeBrBIHrrC8AI+UfFQ5FKQi0DLmYmaK/aCVcas0Jk6bpS7pU6vRC7YruGNL3Yz2hvjCdAYx+rBCCl0qJRAdla5z1Fk5QimSUNIkLM+fQ4s/7n11QV3PqKwtmZUpiteT6xvFA5BskXwXBv8BGGCokrCRw+p4R6vz/ibqKr4IpjMrkCkhdhVbLBe2f0ECJ8nPNVD1hM5srCiqn84/ZG1DaS9Dv3aQYx4+uwJC9JO0w8/sMWbx+qhQelMeUgPOlECtk2FWcN7laK10O4tIIedRumK4egEwRWUpdQQp7oyg4UUEWLOcdLd0la55MIIaWsUnIWatxIaQmMkI/EN5/6uUXYBrGeWLtgJvOtH1f1Ev4B/GIS9e33oVrDdp/EsOHwuG0gz3LeFRFp+Z7G9yea5//K/AOVS85cXez84CWM8qBanF0XIIjZLAF6HKhvcXKIXscuN5rOMxwo3Idy0lbmuHdqlMVPZy68Pkcbq7HtnOmE0UbFRLhav4IaV5Pjuhw0SkT7ouaSe/njTzehGmy/IuiMBFUIpIIF+Yfy3SVnm1nLWsynM9lEb9Zk5tawTIN4w1arO5XDNSu9MKXWkMYgnHk8o7GyOlAa7/oOAfUUqQQ1MmRO6l0UUg8+zb4OR/pfMPxhO1SMoFqlQdSoCFm2RWJa4CdnBnnyWibPDKQGpa75Os2C0qyWsviCJEhAow9jnYFza8YpdC28N3U4zmMzGqSE26vGBqaXbpJDWLdrjvTLQLL/yM36004B6u5J4wAtBHx5TaH4NuTinWQ/crXEHLPgXu5/UuqDqMVSHMmfIpDWljG0V8JW4R1WbLKli5HggHZbsdtRWK71U9cJZIyqG/E6Ww7A1HWnL/b3s1w11iUBLc7gdRApONCn4tXXo0DFv1+eiCCVGeHrDS8WFVXaeGvA+dDPDCyqlWWCUJXW+Y6qktuXsXM6K155xAH0NqblxSD4uhVlhWCdkLEBv4Wpk/oTY1gSWnjO8VGXUYBuBEqQsX8MyRT56lZU56nGNqxSiRwFFS1zDjPOkyquLaq9QV6KBShckqVKHkWmDtYrpioWQrUi9YIR0HqaohvlT2X/V85Q92k72d9WfjUnTnm6VxZfdYwJrFBHXcbDZK3s7Frln2ik4uNbfarvIaWdXlNDTWizyCX/TdmJy5S7wlSH/2m1ozRfEQhvQnI5V2YT0lZxXOv9VVTlErRriUa8+H1kg6pwwhBEa9sFjPE6RklHRJOstJ4W91qAkyDZeNPdkBujHs8KZki/8k2d5vPdCUa79s1Z4Tyme8ooT1W7exR9zwpgXiLJ8clXiyederfOoeOropUNLQqTW1q76InaW6TY2AzmW7WNXa2FQJIaRItU2SUqdTul52WXjFv50o3ZIt8QLv6muldC8hhQ3gKbiSuEMiaKkLhIbnXu+ucWGU9tfy7DwWru5LBW2T8VY3XTnbNlauKS+S5IbNeotyX0oaZIktxUcRGiiReYi7imLIBW10JGIkKBGtMvBC8FrCwxb/WmlyaxGyzxQ1Ec2Hzqorh2uaqtsDwjSCrZWSSjlWtesKWNfChgiLUACOO33tVnjDUbGGLBc/NVWdVd0d9L7s751Ekpzr1AI805JIvyazqCWt7RbepXRqM1XAKhKfCJSiCHpVQyoV50kB6eq+G6ER0KXXSu7u51LimBYCUbTCS6W6srTKo3GJmxIoOb0TRUDNJAVIWHYNp8r3UAhtSax8Q1DWFMjOSp0G1Rm8dpqWBNmsOXUpmfCWGkDl2LYwtAKQqpFIl9ZfeppWoV9T7tLcpDguSIa8KjVEPCbRfUdmkj2EJVJMuxvROkT3s5Yy9SX9cVFJ7xU+irf3EfsY+V6kXQcjaBTT6C/YqOhF0UHwrlxfi9GgLrFiRSjRkdmgSxRREkYoD/9knKteQ3qRQXThi6M9h2BGpeKSpIDBdSWeSsxAyTjEY24gW7O1VbLrWlkESd9MGiRkBzKEXAgzuU9plNINQ/fRa1WExejQ1Y0WuxEGpUK/WwScvl9BN0sKSzQhUq8mnUWtrRZMTQwnHiBKmFaJacWL1pi57Em2uQVJ4X2urpWYmF4SPlwqXF9ePCnCuW4NQFcoQNqCQDMJ1SdVntE0PdKKZATK25qSiibTFDZV4stl1WpGKHfJcvUgBE1/hOr5KDUz8IamT2VK0zJRmhzWeKlUzStRNJ7SBJCI4ymNBwclY9p1VbZrdDgUg9Ye1qMcrEclz94PnIBBwosBYrQRwcsRtL0l7eq+sd0jQvuH7rde1Zi5wMHce6ns6E+KZ0w4aliEIlnedcgDFGlxJW3W1jPsfomTrP7BCxirfBkv2jHPP+23CgS8FCV7jmW0RcZbC6316Br0Zu6OPIOHWV+kV0MZPMpGPHLleo1i3wpXvFERvmfz+L4IsbE2XtJMeI2U1cS7Ol92xTbukc8vGoI/SShvP63ck6oM98uaUAoepOBLd/hLMzAqwjJ1W5f88mtyFKIl62pZQXR5jFB7Wj6Xd09+RauQfYZgeThPCogeYxxBwNQGw+qEM8dX/SWoiXIvt5LuPq1zZNca1yRQYDatEaMmDhZaIU9IxybnrwcdRZd/NF1D51totR2UwOcvgNzSmDh+KPmUpEAa//JRDwVTAegxD5Vw0ewLEqQKJkCXrvPhwUTl/TlhqPlIZIco7fE4OdQ1GsVVS1yZTWm8Y6Ut6HYqWRWC1TUSIx0pab0uD65Wz5dIZ70pwIArxvVXnQ5aBk3vwfIQ1HkCiqX5651APQaz7uduYkKrzKuOy2lBoq70tr/dj4O9wVjuXfpZ1xHRoUanjmvvwNufhzgyu8tjdjWBtsKv0Dn7wyhNqNRDpecoM73GH6PQzBQlvsnyEurZZTlAK+AWzwMW7KUqZ71stCQ6qmESEHrTOxyFDtU7rvlHdMlh1WNVX93sBtdATnkHq3QENUSAdD14tpy/itCpmKTq07qiLeByx3qAK+U6V5vaRNS8awK3xH6WNy62v0J3tCws9ylnvL87CUjt4pZt2wrjwx95SS0HVFSbqL72PSRiqsbYhHaHQVZPVUq1VRr9twYqxxpnz+7vZ8f5roLKxk3V7yvlxdWVDPnHG+BlExX5lDOnzmaMnqPONMNq0tqy19WojZV5TYVVySCIZe3hvcVkhdWsilp4ih9W+yH6fVRzk5E3U1knuaDnLaXVd9zreis3teS89aYeLZw5S9bFnS1vULssMielUT27eLO1lViahtXQMo/VLbkhD3coMz2cKZf9oYw10uXeRzP/qoWEBWVsmhakmtVyDLbUseQvu2WYLDerpKE2tve3etDeZGppgxdJPHKNznFep4mlqYv5FwFG3QeiVZj98RF484PqZIokxfAosYpHsjYdM7PTISUNB2mZ5GJ7GA7J/EMxmRTSI5kLb1AoqhrrTIll1S3jjxtRbQwmC+YzGNT+BKKNxCh3aZ3MFpz8CEdqKQp00S+tfzaorI4ud1RpFMV3CPHpYJQReozWxFXqlkHBFpSfBt1EsMBtDrpEO+57BTkeBbplJyoF5+OPIUGwamVuay/1Zkth7P/+61/rv/O/fwswAG+qhEBjTqKJAAAAAElFTkSuQmCC</cfsavecontent>

	<cfsetting showdebugoutput='#false#'>
	<cfif getBaseTemplatePath() == getCurrentTemplatePath()>	

		<cfapplication name='__LUCEE_STATIC_CONTENT' sessionmanagement='#false#' clientmanagement='#false#' applicationtimeout='#createtimespan( 1, 0, 0, 0 )#'>
				
		<cfset etag 	= '''8F24A177C7198C3FF00ECB35C0CEA965'''>
		<cfset mimetype = 'image/png'>		

		<cfheader name='Expires' value='#getHttpTimeString( now() + 100 )#'>
		<cfheader name='Cache-Control' value='max-age=#86400 * 100#'>		
		<cfheader name='ETag' value='#etag#'>

		<cfif len( CGI.HTTP_IF_NONE_MATCH ) && ( CGI.HTTP_IF_NONE_MATCH == '#etag#' )>

			<!--- etag matches, return 304 !--->
			<cfheader statuscode='304' statustext='Not Modified'>
			<cfcontent reset='#true#' type='#mimetype#'><cfabort>
		</cfif>

		<!--- file was not cached; send the content !--->
		<cfcontent reset='#true#' type='#mimetype#' variable='#toBinary( content )#'><cfabort>
	<cfelse>

		<cfcontent reset='#true#'><cfoutput>content:image/png;base64,#content#</cfoutput><cfabort>
	</cfif>