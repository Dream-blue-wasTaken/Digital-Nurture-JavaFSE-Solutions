import { CanDeactivateFn } from '@angular/router';
export interface ComponentWithDirtyCheck {
  isDirty(): boolean;
}
export const unsavedChangesGuard: CanDeactivateFn<ComponentWithDirtyCheck> = (component) => {
  if (component && component.isDirty && component.isDirty()) {
    return window.confirm('You have unsaved changes. Leave?');
  }
  return true;
};