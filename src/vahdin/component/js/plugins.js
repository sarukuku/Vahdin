// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());

// Place any jQuery/helper plugins in here.

///**
// * StyleFix 1.0.3 & PrefixFree 1.0.7
// * @author Lea Verou
// * MIT license
// */
//(function(){function j(a,c){return[].slice.call((c||document).querySelectorAll(a))}if(window.addEventListener){var g=window.StyleFix={link:function(a){try{if("stylesheet"!==a.rel||a.hasAttribute("data-noprefix"))return}catch(c){return}var i=a.href||a.getAttribute("data-href"),f=i.replace(/[^\/]+$/,""),j=(/^[a-z]{3,10}:/.exec(f)||[""])[0],k=(/^[a-z]{3,10}:\/\/[^\/]+/.exec(f)||[""])[0],h=/^([^?]*)\??/.exec(i)[1],n=a.parentNode,e=new XMLHttpRequest,b;e.onreadystatechange=function(){4===e.readyState&&
//b()};b=function(){var c=e.responseText;if(c&&a.parentNode&&(!e.status||400>e.status||600<e.status)){c=g.fix(c,!0,a);if(f)var c=c.replace(/url\(\s*?((?:"|')?)(.+?)\1\s*?\)/gi,function(a,c,b){return/^([a-z]{3,10}:|#)/i.test(b)?a:/^\/\//.test(b)?'url("'+j+b+'")':/^\//.test(b)?'url("'+k+b+'")':/^\?/.test(b)?'url("'+h+b+'")':'url("'+f+b+'")'}),b=f.replace(/([\\\^\$*+[\]?{}.=!:(|)])/g,"\\$1"),c=c.replace(RegExp("\\b(behavior:\\s*?url\\('?\"?)"+b,"gi"),"$1");b=document.createElement("style");b.textContent=
//c;b.media=a.media;b.disabled=a.disabled;b.setAttribute("data-href",a.getAttribute("href"));n.insertBefore(b,a);n.removeChild(a);b.media=a.media}};try{e.open("GET",i),e.send(null)}catch(r){"undefined"!=typeof XDomainRequest&&(e=new XDomainRequest,e.onerror=e.onprogress=function(){},e.onload=b,e.open("GET",i),e.send(null))}a.setAttribute("data-inprogress","")},styleElement:function(a){if(!a.hasAttribute("data-noprefix")){var c=a.disabled;a.textContent=g.fix(a.textContent,!0,a);a.disabled=c}},styleAttribute:function(a){var c=
//a.getAttribute("style"),c=g.fix(c,!1,a);a.setAttribute("style",c)},process:function(){j('link[rel="stylesheet"]:not([data-inprogress])').forEach(StyleFix.link);j("style").forEach(StyleFix.styleElement);j("[style]").forEach(StyleFix.styleAttribute)},register:function(a,c){(g.fixers=g.fixers||[]).splice(void 0===c?g.fixers.length:c,0,a)},fix:function(a,c,i){for(var f=0;f<g.fixers.length;f++)a=g.fixers[f](a,c,i)||a;return a},camelCase:function(a){return a.replace(/-([a-z])/g,function(a,g){return g.toUpperCase()}).replace("-",
//"")},deCamelCase:function(a){return a.replace(/[A-Z]/g,function(a){return"-"+a.toLowerCase()})}};setTimeout(function(){j('link[rel="stylesheet"]').forEach(StyleFix.link)},10);document.addEventListener("DOMContentLoaded",StyleFix.process,!1)}})();
//(function(j){function g(d,b,c,e,f){d=a[d];d.length&&(d=RegExp(b+"("+d.join("|")+")"+c,"gi"),f=f.replace(d,e));return f}if(window.StyleFix&&window.getComputedStyle){var a=window.PrefixFree={prefixCSS:function(d,b){var c=a.prefix;-1<a.functions.indexOf("linear-gradient")&&(d=d.replace(/(\s|:|,)(repeating-)?linear-gradient\(\s*(-?\d*\.?\d*)deg/ig,function(a,d,b,c){return d+(b||"")+"linear-gradient("+(90-c)+"deg"}));d=g("functions","(\\s|:|,)","\\s*\\(","$1"+c+"$2(",d);d=g("keywords","(\\s|:)","(\\s|;|\\}|$)",
//"$1"+c+"$2$3",d);d=g("properties","(^|\\{|\\s|;)","\\s*:","$1"+c+"$2:",d);if(a.properties.length)var e=RegExp("\\b("+a.properties.join("|")+")(?!:)","gi"),d=g("valueProperties","\\b",":(.+?);",function(a){return a.replace(e,c+"$1")},d);b&&(d=g("selectors","","\\b",a.prefixSelector,d),d=g("atrules","@","\\b","@"+c+"$1",d));d=d.replace(RegExp("-"+c,"g"),"-");return d=d.replace(/-\*-(?=[a-z]+)/gi,a.prefix)},property:function(d){return(a.properties.indexOf(d)?a.prefix:"")+d},value:function(d){d=g("functions",
//"(^|\\s|,)","\\s*\\(","$1"+a.prefix+"$2(",d);return d=g("keywords","(^|\\s)","(\\s|$)","$1"+a.prefix+"$2$3",d)},prefixSelector:function(d){return d.replace(/^:{1,2}/,function(d){return d+a.prefix})},prefixProperty:function(d,b){var c=a.prefix+d;return b?StyleFix.camelCase(c):c}},c={},i=[],f=getComputedStyle(document.documentElement,null),p=document.createElement("div").style,k=function(a){if("-"===a.charAt(0)){i.push(a);var a=a.split("-"),b=a[1];for(c[b]=++c[b]||1;3<a.length;)a.pop(),b=a.join("-"),
//StyleFix.camelCase(b)in p&&-1===i.indexOf(b)&&i.push(b)}};if(0<f.length)for(var h=0;h<f.length;h++)k(f[h]);else for(var n in f)k(StyleFix.deCamelCase(n));var h=0,e,b;for(b in c)f=c[b],h<f&&(e=b,h=f);a.prefix="-"+e+"-";a.Prefix=StyleFix.camelCase(a.prefix);a.properties=[];for(h=0;h<i.length;h++)n=i[h],0===n.indexOf(a.prefix)&&(e=n.slice(a.prefix.length),StyleFix.camelCase(e)in p||a.properties.push(e));"Ms"==a.Prefix&&(!("transform"in p)&&!("MsTransform"in p)&&"msTransform"in p)&&a.properties.push("transform",
//"transform-origin");a.properties.sort();e=function(a,b){r[b]="";r[b]=a;return!!r[b]};b={"linear-gradient":{property:"backgroundImage",params:"red, teal"},calc:{property:"width",params:"1px + 5%"},element:{property:"backgroundImage",params:"#foo"},"cross-fade":{property:"backgroundImage",params:"url(a.png), url(b.png), 50%"}};b["repeating-linear-gradient"]=b["repeating-radial-gradient"]=b["radial-gradient"]=b["linear-gradient"];h={initial:"color","zoom-in":"cursor","zoom-out":"cursor",box:"display",
//flexbox:"display","inline-flexbox":"display",flex:"display","inline-flex":"display"};a.functions=[];a.keywords=[];var r=document.createElement("div").style,l;for(l in b)k=b[l],f=k.property,k=l+"("+k.params+")",!e(k,f)&&e(a.prefix+k,f)&&a.functions.push(l);for(var m in h)f=h[m],!e(m,f)&&e(a.prefix+m,f)&&a.keywords.push(m);l=function(a){s.textContent=a+"{}";return!!s.sheet.cssRules.length};m={":read-only":null,":read-write":null,":any-link":null,"::selection":null};e={keyframes:"name",viewport:null,
//document:'regexp(".")'};a.selectors=[];a.atrules=[];var s=j.appendChild(document.createElement("style")),q;for(q in m)b=q+(m[q]?"("+m[q]+")":""),!l(b)&&l(a.prefixSelector(b))&&a.selectors.push(q);for(var t in e)b=t+" "+(e[t]||""),!l("@"+b)&&l("@"+a.prefix+b)&&a.atrules.push(t);j.removeChild(s);a.valueProperties=["transition","transition-property"];j.className+=" "+a.prefix;StyleFix.register(a.prefixCSS)}})(document.documentElement);

/** cssParentSelector 1.0.11 | MIT and GPL Licenses | git.io/cssParentSelector */

(function($) {

    $.fn.cssParentSelector = function() {
        var k = 0, i, j,

             // Class that's added to every styled element
            CLASS = 'CPS',

            stateMap = {
                hover: 'mouseover mouseout',
                checked: 'click',
                focus: 'focus blur',
                active: 'mousedown mouseup',
                selected: 'change',
                changed: 'change'
            },

            attachStateMap = {
                mousedown: 'mouseout'
            },

            detachStateMap = {
                mouseup: 'mouseout'
            },

            pseudoMap = {
                'after': 'appendTo',
                'before': 'prependTo'
            },

            pseudo = {},

            parsed, matches, selectors, selector,
            parent, target, child, state, declarations,
            pseudoParent, pseudoTarget,

            REGEXP = [
                /[\w\s\.\-\:\=\[\]\(\)\'\*\"\^#]*(?=!)/,
                /[\w\s\.\-\:\=\[\]\(\)\,\*\^$#>!]+/,
                /[\w\s\.\-\:\=\[\]\'\,\"#>]*\{{1}/,
                /[\w\s\.\-\:\=\'\*\|\?\^\+\/\\;#%]+\}{1}/
            ],

            REGEX = new RegExp((function(REGEXP) {
                var ret = '';

                for (var i = 0; i < REGEXP.length; i++)
                    ret += REGEXP[i].source;

                return ret;
            })(REGEXP), "gi"),

            parse = function(css) {

                // Remove comments.
                css = css.replace(/(\/\*([\s\S]*?)\*\/)/gm, '');

                if ( matches = css.match(REGEX) ) {

                    parsed = '';
                    for (i = -1; matches[++i], style = matches[i];) {

                        // E! P > F, E F { declarations } => E! P > F, E F
                        selectors = style.split('{')[0].split(',');

                        // E! P > F { declarations } => declarations
                        declarations = '{' + style.split(/\{|\}/)[1].replace(/^\s+|\s+$[\t\n\r]*/g, '') + '}';

                        // There's nothing so we can skip this one.
                        if ( declarations === '{}' ) continue;

                        declarations = declarations.replace(/;/g, ' !important;');

                        for (j = -1; selectors[++j], selector = $.trim(selectors[j]);) {

                            j && (parsed += ',');

                            if (/!/.test(selector) ) {

                                // E! P > F => E
                                parent = $.trim(selector.split('!')[0].split(':')[0]);

                                // E! P > F => P
                                target = $.trim(selector.split('!')[1].split('>')[0].split(':')[0]) || []._;

                                // E:after! P > after
                                pseudoParent = $.trim(selector.split('>')[0].split('!')[0].split(':')[1]) || []._;

                                // E! P:after > after
                                pseudoTarget = target ? ($.trim(selector.split('>')[0].split('!')[1].split(':')[1]) || []._) : []._;

                                // E! P > F => F
                                child    = $($.trim(selector.split('>')[1]).split(':')[0]);

                                // E! P > F:state => state
                                state = (selector.split('>')[1].split(/:+/)[1] || '').split(' ')[0] || []._;


                                child.each(function(i) {

                                    var subject = $(this)[parent == '*' ? 'parent' : 'closest'](parent);

                                    pseudoParent && (subject = pseudoMap[pseudoParent] ?
                                        $('<div></div>')[pseudoMap[pseudoParent]](subject) :
                                        subject.filter(':' + pseudoParent));

                                    target && (subject = subject.find(target));

                                    target && pseudoTarget && (subject = pseudoMap[pseudoTarget] ?
                                        $('<div></div>')[pseudoMap[pseudoTarget]](subject) :
                                        subject.filter(':' + pseudoTarget));

                                    var id = CLASS + k++,
                                        toggleFn = function(e) {

                                            e && attachStateMap[e.type] &&
                                                $(subject).one(attachStateMap[e.type], function() {$(subject).toggleClass(id) });

                                            e && detachStateMap[e.type] &&
                                                $(subject).off(detachStateMap[e.type]);

                                            $(subject).toggleClass(id)
                                        };

                                    i && (parsed += ',');

                                    parsed += '.' + id;
                                    ! state ? toggleFn() : $(this).on( stateMap[state] || state , toggleFn );

                                });
                            } else {
                                parsed += selector;
                            }
                        }

                        parsed += declarations;

                    };

                    $('<style type="text/css">' + parsed + '</style>').appendTo('head');

                };

            };

        $('link[rel=stylesheet], style').each(function() {
            $(this).is('link') ?
                $.get(this.href).success(function(css) { parse(css); }) : parse($(this).text());
        });

    };

    $().cssParentSelector();

})(jQuery);
