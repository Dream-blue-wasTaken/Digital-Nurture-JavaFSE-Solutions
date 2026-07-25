import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';
@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  @Input('appHighlight') highlightColor: string = 'yellow';
  constructor(private el: ElementRef, private renderer: Renderer2) {}
  @HostListener('mouseenter') onMouseEnter() {
    const color = this.highlightColor || 'yellow';
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', color);
  }
  @HostListener('mouseleave') onMouseLeave() {
    this.renderer.removeStyle(this.el.nativeElement, 'backgroundColor');
  }
}